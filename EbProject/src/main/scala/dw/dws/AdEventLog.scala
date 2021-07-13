package dw.dws

import modes.{AdClick, CountByProductAd}
import myutils.SourceKafka
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.concurrent.TimeUnit

import akka.stream.actor.WatermarkRequestStrategy
import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, Watermark, WatermarkGenerator, WatermarkGeneratorSupplier, WatermarkOutput, WatermarkStrategy}
import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.util.Collector

/**
 * 需求3：每隔5秒统计最近1小时内广告的点击量---增量
 */
object AdEventLog {

  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    import org.apache.flink.api.scala._

    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime) //设置时间特征为事件时间

    //从kafka中获取数据（flume->kafka->flink）
    val kafkaSource: FlinkKafkaConsumer[String] = new SourceKafka().getKafkaSource("eventlog")

    val eventLogStream: DataStream[String] = env.addSource(kafkaSource)
    //    eventLogStream.print()
    /**
     * area:
     * uid:
     * product_id:
     * time:
     */
    val mapEventStream: DataStream[AdClick] = eventLogStream.map(x => {
      val jsonObj: JSONObject = JSON.parseObject(x)

      val attr: String = jsonObj.get("attr").toString
      val attrJson: JSONObject = JSON.parseObject(attr)
      val area: String = attrJson.get("area").toString
      val uid: String = attrJson.get("uid").toString

      val eventData: String = jsonObj.get("lagou_event").toString
      val datas: JSONArray = JSON.parseArray(eventData)
      val list = new java.util.ArrayList[String]()

      datas.forEach(x => list.add(x.toString))

      var productId: String = null
      var timestamp: Long = 0L


      list.forEach(x => {
        val xJson: JSONObject = JSON.parseObject(x)
        if (xJson.get("name").toString.equals("ad")) {
          val jsonData: String = xJson.get("json").toString
          val jsonDatas = JSON.parseObject(jsonData)

          productId = jsonDatas.get("product_id").toString
          timestamp = TimeUnit.MILLISECONDS.toSeconds(xJson.get("time").toString.toLong)
        }
      })

      AdClick(area, uid, productId, timestamp)
    })

    val filtered: DataStream[AdClick] = mapEventStream.filter(x => x.productId != null)
    //    filtered.print()


    //水印策略 原生
    val watermarkStrategy: WatermarkStrategy[AdClick] = new WatermarkStrategy[AdClick] {
      override def createWatermarkGenerator(context: WatermarkGeneratorSupplier.Context): WatermarkGenerator[AdClick] = {
        new WatermarkGenerator[AdClick] {
          var maxtimestamp: Long = 0L
          var maxOutOfOrder: Long = 500L

          override def onEvent(event: AdClick, eventTimestamp: Long, output: WatermarkOutput): Unit = {
            maxtimestamp = Math.max(maxtimestamp, event.timestamp)
          }

          override def onPeriodicEmit(output: WatermarkOutput): Unit = {
            output.emitWatermark(new Watermark(maxtimestamp - maxOutOfOrder))
          }
        }
      }
    }
//      .withTimestampAssigner((element, timestamp)=> element.timestamp)
      .withTimestampAssigner(new SerializableTimestampAssigner[AdClick] {
        override def extractTimestamp(element: AdClick, recordTimestamp: Long): Long = {
          element.timestamp
        }
      })

    //水印策略 Flink已经实现好的方法
   /* val watermarkStrategy: WatermarkStrategy[Nothing] = WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(1))
      .withTimestampAssigner((element: AdClick, timestamp) => element.timestamp)
*/
    /*val watermarked: DataStream[AdClick] = filtered.assignTimestampsAndWatermarks(watermarkStrategy)
    val keyed: KeyedStream[AdClick, String] = watermarked.keyBy(_.productId)
    val windowed: WindowedStream[AdClick, String, TimeWindow] = keyed.timeWindow(Time.seconds(20), Time.seconds(10))
    val result: DataStream[CountByProductAd] = windowed.aggregate(new AdAggFunc, new AdWindowFunc())*/

    //从kafka上获取数据源可以确定是有序无界的
    val result: DataStream[CountByProductAd] = filtered
      .assignAscendingTimestamps(x => x.timestamp)
      .keyBy(_.productId)
     .timeWindow(Time.seconds(20), Time.seconds(10))
      .aggregate(new AdAggFunc, new AdWindowFunc())

    result.print()

    env.execute()
  }

  class AdAggFunc() extends AggregateFunction[AdClick, Long, Long] {
    override def createAccumulator(): Long = 0L

    override def add(ad: AdClick, acc: Long): Long = acc + 1

    override def getResult(acc: Long): Long = acc

    override def merge(acc1: Long, acc2: Long): Long = acc1 + acc2
  }

  class AdWindowFunc() extends WindowFunction[Long, CountByProductAd, String, TimeWindow] {

    private def formatTs(ts: Long) = {
      val df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      df.format(new Date(ts))
    }

    override def apply(key: String,
                       window: TimeWindow,
                       input: Iterable[Long],
                       out: Collector[CountByProductAd]): Unit = {

      out.collect(CountByProductAd(formatTs(window.getEnd * 1000), key, input.iterator.next()))
    }

  }

}


