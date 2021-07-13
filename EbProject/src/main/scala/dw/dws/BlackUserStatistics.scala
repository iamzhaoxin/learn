package dw.dws

import java.{lang, util}
import java.util.concurrent.TimeUnit

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import modes.{AdClick, BlackUser}
import myutils.SourceKafka
import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, Watermark, WatermarkGenerator, WatermarkGeneratorSupplier, WatermarkOutput, WatermarkStrategy}
import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.streaming.api.scala.{DataStream, KeyedStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.api.scala._
import org.apache.flink.cep.PatternSelectFunction
import org.apache.flink.cep.functions.PatternProcessFunction
import org.apache.flink.cep.scala.{CEP, PatternStream}
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.function.WindowFunction
//import org.apache.flink.streaming.api.functions.windowing.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * 需求4：显示：黑名单用户ID、广告ID、点击数
 */
object BlackUserStatistics {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
//    env.setParallelism(1)
    val kafkaConsumer: FlinkKafkaConsumer[String] = new SourceKafka().getKafkaSource("eventlog")
//    kafkaConsumer.setStartFromEarliest()
    kafkaConsumer.setStartFromLatest()
    val data: DataStream[String] = env.addSource(kafkaConsumer)

//    data.print()
    /*
    area/uid/productId/timestamp
     */
    val adClickStream: DataStream[AdClick] = data.map(x => {
      val adJsonObject: JSONObject = JSON.parseObject(x)
      val attrObject: JSONObject = adJsonObject.getJSONObject("attr")
      val area: String = attrObject.get("area").toString
      val uid: String = attrObject.get("uid").toString
      var productId: String = null
      var timestamp: Long = 0L
      val array: JSONArray = adJsonObject.getJSONArray("lagou_event")
      array.forEach(x => {
        val nObject: JSONObject = JSON.parseObject(x.toString)
        if (nObject.get("name").equals("ad")) {
          val adObject: JSONObject = nObject.getJSONObject("json")
          productId = adObject.get("product_id").toString
//          timestamp = TimeUnit.MICROSECONDS.toSeconds(nObject.get("time").toString.toLong)
          timestamp = nObject.get("time").toString.toLong
        }
      })
      AdClick(area, uid, productId, timestamp)
    })

    //watermark
    val watermarked: DataStream[AdClick] = adClickStream.assignTimestampsAndWatermarks(new WatermarkStrategy[AdClick] {
      override def createWatermarkGenerator(context: WatermarkGeneratorSupplier.Context): WatermarkGenerator[AdClick] = {
        new WatermarkGenerator[AdClick] {
          var maxtimestamp = 0L
          var maxOutofOrderness = 500L

          override def onEvent(event: AdClick, eventTimestamp: Long, output: WatermarkOutput): Unit = {
            maxtimestamp = Math.max(maxtimestamp, event.timestamp)
          }

          override def onPeriodicEmit(output: WatermarkOutput): Unit = {
            output.emitWatermark(new Watermark(maxtimestamp - maxOutofOrderness))
          }
        }
      }
    }.withTimestampAssigner(new SerializableTimestampAssigner[AdClick] {
      override def extractTimestamp(element: AdClick, recordTimestamp: Long): Long = {
        element.timestamp
      }
    })
    )

    val keyed: KeyedStream[AdClick, (String, String)] = watermarked.keyBy(x => (x.uid, x.productId))

    //方式一：通过FlinkCEP模式匹配的方式
    /*val pattened: Pattern[AdClick, AdClick] = Pattern.begin[AdClick]("begin").timesOrMore(10).within(Time.seconds(10))
    val pattenStream: PatternStream[AdClick] = CEP.pattern(keyed, pattened)
    val result: DataStream[BlackUser] = pattenStream.process(new PatternProcessFunction[AdClick, BlackUser] {
      override def processMatch(`match`: util.Map[String, util.List[AdClick]], ctx: PatternProcessFunction.Context, out: Collector[BlackUser]): Unit = {
        println(`match`)
        out.collect(BlackUser(`match`.get("begin").get(0).uid, `match`.get("begin").get(0).productId, 10))
      }
    })*/

    //方式二：自己实现
    val value: DataStream[BlackUser] = adClickStream.keyBy(x => (x.uid, x.productId))
      .timeWindow(Time.seconds(10))
      .aggregate(new BlackAggFunc, new BlackWindowFunc)

    val result: DataStream[BlackUser] = value.filter(_.count > 10)
    result.print()

    env.execute()
  }

  class BlackAggFunc extends AggregateFunction[AdClick,Long,Long] {
    override def createAccumulator(): Long = 0L

    override def add(value: AdClick, accumulator: Long): Long = accumulator + 1

    override def getResult(accumulator: Long): Long = accumulator

    override def merge(a: Long, b: Long): Long = a + b
  }

  class BlackWindowFunc extends WindowFunction[Long,BlackUser,(String,String),TimeWindow] {
    override def apply(key: (String, String), window: TimeWindow, input: Iterable[Long], out: Collector[BlackUser]): Unit = {
      out.collect(BlackUser(key._1,key._2,input.iterator.next()))
    }
  }

}
