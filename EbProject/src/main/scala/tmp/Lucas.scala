package tmp

import java.text.SimpleDateFormat

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import modes.{ChanalDetail2, CountByChannal2}
import myutils.SourceKafka
import org.apache.flink.api.common.functions.{AggregateFunction, MapFunction}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.table.data.util.DataFormatConverters.TimeConverter
import org.apache.flink.util.Collector

object Lucas {
  private val sdf = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss")
  def main(args: Array[String]): Unit = {
    //
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    //env从kafka上获取数据
    val consumer: FlinkKafkaConsumer[String] = new SourceKafka().getKafkaSource("eventlog")
    val data: DataStream[String] = env.addSource(consumer)
//    data.print()

    val maped: DataStream[ChanalDetail2] = data.map(new MapFunction[String, ChanalDetail2] {
      override def map(value: String): ChanalDetail2 = {
        val nObject: JSONObject = JSON.parseObject(value)
        val attr: JSONObject = nObject.getJSONObject("attr")
        val uid: String = attr.getString("uid")
        val channel: String = attr.getString("channel")
        val lagou_event: JSONArray = nObject.getJSONArray("lagou_event")
        var time = "0"
        if (!lagou_event.isEmpty) {
          time = lagou_event.getJSONObject(0).getString("time")
        }
        ChanalDetail2(uid, channel, time)
      }
    })
    val keyed: KeyedStream[ChanalDetail2, String] = maped.assignAscendingTimestamps(_.ts.toLong).keyBy(_.chanal)
    val windowed: WindowedStream[ChanalDetail2, String, TimeWindow] = keyed.timeWindow(Time.hours(12))
    val result: DataStream[CountByChannal2] = windowed.aggregate(new MyAggFun, new MyWinFun)
    val value: DataStream[CountByChannal2] = result.filter(x => {
      x.channal == "公司活动"
    })
    value.print()

    env.execute()
    //样例类
  }

  class MyAggFun extends AggregateFunction[ChanalDetail2, Long, Long] {
    override def createAccumulator(): Long = 0L

    override def add(value: ChanalDetail2, accumulator: Long): Long = accumulator + 1

    override def getResult(accumulator: Long): Long = accumulator

    override def merge(a: Long, b: Long): Long = a + b
  }

  class MyWinFun extends WindowFunction[Long, CountByChannal2, String, TimeWindow] {
    override def apply(key: String, window: TimeWindow, input: Iterable[Long], out: Collector[CountByChannal2]): Unit = {
      out.collect(CountByChannal2(key,input.iterator.next(), sdf.format(window.getStart)))
    }
  }

}





























