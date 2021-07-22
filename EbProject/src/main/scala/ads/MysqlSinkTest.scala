package ads

import modes.CityOrder

import java.sql.{Connection, DriverManager, PreparedStatement}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.configuration.Configuration
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object MysqlSinkTest {
  private val URL: String = "jdbc:mysql://linux123:3306/dwshow?characterEncoding=utf8&useSSL=false"
  private val USER: String = "root"
  private val PASSWORD: String = "12345678"

//  def main(args: Array[String]): Unit = {
//    val params: ParameterTool = ParameterTool.fromArgs(args)
//    val runType:String = params.get("runtype")
//    println("runType: " + runType)
//
//    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
//    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
////    env.enableCheckpointing(5000) // checkpoint every 5000
////    env.setStateBackend(new FsStateBackend("file:///tmp/lucas"))
//
//    val inputStream: DataStream[String] = env.socketTextStream("linux121", 7777)
//    // 处理inputStream，包装成Person类
//    val streaming: DataStream[Person] = inputStream.map(line => {
//      println(line)
//      val strings: Array[String] = line.split(",")
//      Person(strings(0).trim, strings(1).trim.toInt, strings(2).trim, strings(3).trim.toFloat)
//    })
//
//    streaming.addSink(new MyJdbcSink).setParallelism(1)
//
//    env.execute("Mysql Sink")
//
//  }

  //case class Person (name: String, age: Int, gender: String, height: Float)
  //case class CityOrder(city:String, province:String, totalMoney:Double, totalCount:Int)

  /**
   * 若选择SinkFunction "Writes the given value to the sink. This function is called for every record."
   * RichFunction有生命周期和初始化配置功能，在初始化时创建连接，后面直接调用连接
   */
  class MyJdbcSink extends RichSinkFunction[(CityOrder, Long)] {
    // 定义一些变量：JDBC连接、sql预编译器()
    var conn: Connection = _
    var updateStmt: PreparedStatement = _
    var insertStmt: PreparedStatement = _

    // open函数用于初始化富函数运行时的上下文等环境，如JDBC连接
    override def open(parameters: Configuration): Unit = {
      println("----------------------------open函数初始化JDBC连接及预编译sql-------------------------")
      super.open(parameters)
      conn = DriverManager.getConnection(URL, USER, PASSWORD)
      insertStmt = conn.prepareStatement("INSERT INTO city_order (city, province, totalMoney, totalCount) VALUES (?, ?, ?, ?)")
      updateStmt = conn.prepareStatement("UPDATE city_order set province = ?, totalMoney = ?, totalCount = ? where city = ?")
    }
    // 调JDBC连接，执行SQL
     override def invoke(value: (CityOrder,Long), context: SinkFunction.Context[_]): Unit = {
      println("-------------------------执行update sql---------------------------")
      // 执行更新语句
      updateStmt.setString(1, value._1.province)
      updateStmt.setDouble(2, value._1.totalMoney)
      updateStmt.setInt(3, value._1.totalCount)
      updateStmt.setString(4, value._1.city)
      updateStmt.execute()

      // 如果update没有查到数据，那么执行insert语句



//      insertStmt.setString(1, value.name)
//      insertStmt.setInt(2, value.age)
//      insertStmt.setString(3, value.gender)
//      insertStmt.setDouble(4, value.height)
//      insertStmt.execute()

      if (updateStmt.getUpdateCount == 0) {
        println("-------------------------执行insert sql---------------------------")
        insertStmt.setString(1, value._1.city)
        insertStmt.setString(2, value._1.province)
        insertStmt.setDouble(3, value._1.totalMoney)
        insertStmt.setInt(4, value._1.totalCount)
        insertStmt.execute()
      }
    }
    // 关闭时做清理工作
    override def close(): Unit = {
      println("-----------------------关闭连接，并释放资源-----------------------")
//      updateStmt.close()
      insertStmt.close()
      conn.close()
    }
  }

}































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































