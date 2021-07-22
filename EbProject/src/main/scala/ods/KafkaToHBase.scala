package ods


import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import modes.TableObject
import myutils.SourceKafka
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._
import java.util
/**
 * 1.从kafka的test这个topic获取数据-----FlinkKafkaConsumer
 * 2.把获取到的json格式的数据进行格式转化-----fastjson
 * type,database, table,data(jsonArray)
 * 3.把转化好的数据保存到HBase中 nosql
 */
object KafkaToHBase {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val kafkaConsumer = new SourceKafka().getKafkaSource("test")
    //    kafkaConsumer.setStartFromLatest()
    kafkaConsumer.setStartFromEarliest()
    val sourceStream = env.addSource(kafkaConsumer)
    sourceStream.print()
    //type,database, table,data(jsonArray)

    /*val value: DataStream[List[MyBean]] = sourceStream.map(x => {
          val jsonObj: JSONObject = JSON.parseObject(x)
          val database: AnyRef = jsonObj.get("database")
          val table: AnyRef = jsonObj.get("table")
          val typeInfo: AnyRef = jsonObj.get("type")
          var obs: List[MyBean] = Nil
          val array: JSONArray = jsonObj.getJSONArray("data")
          if (array != null) {
            array.forEach(x => {
              obs = new MyBean(database.toString, table.toString, typeInfo.toString, x.toString) :: obs
            })
          }
          obs
        })*/

    //canal获取mysql的binlog数据
    /*val value: DataStream[List[TableObject]] = sourceStream.map(x => {
      val jsonObj: JSONObject = JSON.parseObject(x)
      val database: AnyRef = jsonObj.get("database")
      val table: AnyRef = jsonObj.get("table")
      val typeInfo: AnyRef = jsonObj.get("type")
      var obs: List[TableObject] = Nil
      val array: JSONArray = jsonObj.getJSONArray("data")
      if (array != null) {
        array.forEach(x => {
          obs = TableObject(database.toString, table.toString, typeInfo.toString, x.toString) :: obs
        })
      }
      obs
    })
    value.print()*/

    val mapped: DataStream[util.ArrayList[TableObject]] = sourceStream.map(x => {
      val jsonObj: JSONObject = JSON.parseObject(x)
      val database: AnyRef = jsonObj.get("database")
      val table: AnyRef = jsonObj.get("table")
      val typeInfo: AnyRef = jsonObj.get("type")

      val objects = new util.ArrayList[TableObject]()
      val array: JSONArray = jsonObj.getJSONArray("data")
      if(array != null) {
        array.forEach(x => {
          print(database.toString + ".." + table.toString + "..." + typeInfo.toString + ".." + x.toString)
          objects.add(TableObject(database.toString, table.toString, typeInfo.toString, x.toString))
        })
      }

      objects
    })
    //    mapped.print()

    /**
     * 将数据下沉到HBase中保存
     * 1.拿到当前的数据
     * 2、addSink（）--- 自定义下沉器SinkHBase
     */
    System.out.println("here1")
    mapped.addSink(new SinkHBase)



    env.execute()
    System.out.println("here2")
  }
}
