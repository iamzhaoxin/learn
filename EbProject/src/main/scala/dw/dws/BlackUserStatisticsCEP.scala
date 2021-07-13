package dw.dws

import java.util
import java.util.concurrent.TimeUnit

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import modes.AdClick
import myutils.SourceKafka
import org.apache.flink.cep.PatternSelectFunction
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.cep.scala.{CEP, PatternStream}
import org.apache.flink.streaming.api.scala.{DataStream, KeyedStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.api.scala._

/**
 * 需求4：通过FlinkCEP的方式 显示：黑名单用户ID、广告ID、点击数
 */
object BlackUserStatisticsCEP {
  case class UserAction(userId:Long,action:String)
  case class MyPattern(firstAction:String, secondAction:String)
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val kafkaConsumer: FlinkKafkaConsumer[String] = new SourceKafka().getKafkaSource("eventlog")
    kafkaConsumer.setStartFromEarliest()

    val data: DataStream[String] = env.addSource(kafkaConsumer)

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
          timestamp = TimeUnit.MICROSECONDS.toSeconds(nObject.get("time").toString.toLong)
        }
      })
      AdClick(area, uid, productId, timestamp)
    })
//    adClickStream.print()

    val timeStream: DataStream[AdClick] = adClickStream.assignAscendingTimestamps(_.timestamp * 1000L)

    val keyed: KeyedStream[AdClick, String] = timeStream.keyBy(_.uid)

    val pattern: Pattern[AdClick, AdClick] = Pattern.begin[AdClick]("start")
      .where(_.uid != null)
      .times(10)//正好10次
      .timesOrMore(10)//至少10次

      .within(Time.seconds(10))

    val patternStream: PatternStream[AdClick] = CEP.pattern(keyed, pattern)
    val result: DataStream[AdClick] = patternStream.select(new adClickSelectFunc)
    result.print() // 会打印第10次，11，等n次
    env.execute()
  }


  class adClickSelectFunc extends PatternSelectFunction[AdClick, AdClick]{
    override def select(pattern: util.Map[String, util.List[AdClick]]): AdClick = {
      println(pattern)
      pattern.get("start").iterator().next()
//      pattern.get("start").iterator().next()
    }
  }

}
