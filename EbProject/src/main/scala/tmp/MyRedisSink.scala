package tmp

import java.util

import modes.CityOrder
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import redis.clients.jedis.Jedis

class MyRedisSink extends RichSinkFunction[Tuple2[CityOrder,Long]]{
  var jedis:Jedis = null;

  override def open(parameters: Configuration): Unit = {
    //    val parameters: ExecutionConfig.GlobalJobParameters = getRuntimeContext.getExecutionConfig.getGlobalJobParameters
    //    parameters
    jedis = new Jedis("hdp-1", 6379, 6000)
    jedis.auth("lucas")
    jedis.select(0)

  }

  override def invoke(value: (CityOrder,Long), context: SinkFunction.Context[_]): Unit = {
    if(!jedis.isConnected) {
      jedis.connect()
    }
//    jedis.hset("cityorder",value._1.province+value._1.city,value._1.totalCount.toString,value._2.toString)
val map = new util.HashMap[String, String]()
    map.put("totalMoney",value._1.totalMoney.toString)
    map.put("totalCount",value._1.totalCount.toString)
    map.put("time",value._2.toString)
    jedis.hmset(value._1.province+value._1.city,map)
  }

  override def close(): Unit = {
    jedis.close()
  }









}
