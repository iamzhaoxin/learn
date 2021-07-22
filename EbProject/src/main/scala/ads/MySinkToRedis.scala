package ads

import java.util

import modes.CityOrder
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import redis.clients.jedis.Jedis

/**
 * 自定义下沉器，下沉结果数据到Redis中
 */
class MySinkToRedis extends RichSinkFunction[(CityOrder, Long)] {

  var jedis: Jedis = null

  override def open(parameters: Configuration): Unit = {
    jedis = new Jedis("hadoop5", 6379, 6000)
    //    jedis.auth("lucas")
    jedis.select(0)
  }

  //(CityOrder(青岛市,山东省,8178.2,1),1608282220000)
  override def invoke(value: (CityOrder, Long), context: SinkFunction.Context[_]): Unit = {
//    println("value:" + value)
    if (!jedis.isConnected) {
      jedis.connect()
    }
   /* val str: String = "totalmoney:" + value._1.totalMoney.toString + "totalCount:" + value._1.totalCount.toString + "time:" + value._2.toString
    jedis.set(value._1.province + value._1.city.toString, str)*/

    val map = new util.HashMap[String, String]()
    map.put("totalMoney", value._1.totalMoney.toString)
    map.put("totalCount", value._1.totalCount.toString)
    map.put("time", value._2.toString)

    //    jedis.set("a","aa")
    //    print(value._1.totalMoney.toString + "totalCount",value._1.totalCount.toString + value._2.toString)
    if (!map.isEmpty) {
      println(value._1.province + value._1.city.toString + map.size() + map.get("totalMoney") + map.get("totalCount") + map.get("time"))
      try {

        jedis.hset(value._1.province + value._1.city.toString, map)
        map.clear()
      } catch {
        case e: Exception => print(e)
      }
    }
    //    jedis.hse7
  }

  override def close(): Unit = {
    jedis.close()
  }
}
