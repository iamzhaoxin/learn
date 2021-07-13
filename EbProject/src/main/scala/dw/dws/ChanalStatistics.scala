package dw.dws


import com.alibaba.fastjson.{JSON, JSONObject}
import modes.{ChanalDetail, CountByChannal}
import myutils.SourceKafka
import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.streaming.api.scala.{DataStream, KeyedStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.util.Collector

/**
 * 需求5：实时统计各渠道来源用户数量
 */
object ChanalStatistics {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val kafkaConsumer: FlinkKafkaConsumer[String] = new SourceKafka().getKafkaSource("eventlog")
    kafkaConsumer.setStartFromEarliest()
    val data: DataStream[String] = env.addSource(kafkaConsumer)
//    data.print()

    /**
     * chanal/uid
     */
    val chanalDetailStream: DataStream[ChanalDetail] = data.map(x => {
      val jsonObj: JSONObject = JSON.parseObject(x.toString)
      val attrObject: JSONObject = jsonObj.getJSONObject("attr")
      val chanal: String = attrObject.get("channel").toString
      val uid: String = attrObject.get("uid").toString
      ChanalDetail(chanal, uid)
    })
//    chanalDetailStream.print()

    val keyed: KeyedStream[ChanalDetail, String] = chanalDetailStream.keyBy(_.chanal)
    val value: DataStream[CountByChannal] = keyed.timeWindow(Time.seconds(10))
      .aggregate(new ChanalAggFunc, new ChanalWindowFunc)

    val result: DataStream[String] = value.process(new ProcessPrint)
    result.print()
    env.execute()

  }

  class ChanalAggFunc extends AggregateFunction[ChanalDetail,Long,Long] {
    override def createAccumulator(): Long = 0L

    override def add(value: ChanalDetail, accumulator: Long): Long = accumulator + 1

    override def getResult(accumulator: Long): Long = accumulator

    override def merge(a: Long, b: Long): Long = a + b
  }

  class ChanalWindowFunc extends WindowFunction[Long,CountByChannal,String,TimeWindow] {
    override def apply(key: String, window: TimeWindow, input: Iterable[Long], out: Collector[CountByChannal]): Unit = {
      out.collect(CountByChannal(key,input.iterator.next()))
    }
  }

  class ProcessPrint extends ProcessFunction[CountByChannal,String] {
    override def processElement(value: CountByChannal, ctx: ProcessFunction[CountByChannal, String]#Context, out: Collector[String]): Unit = {
      val messag = s"渠道：${value.chanal}的来源用户数量：${value.count}"
      out.collect(messag)
    }
  }

}
