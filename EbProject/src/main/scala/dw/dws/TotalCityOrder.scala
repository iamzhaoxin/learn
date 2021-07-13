package dw.dws

import dim.{AreaDetail, DimArea}
import myutils.MongoSink
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._
import org.apache.flink.table.api.Table
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import org.apache.flink.types.Row
/**
 * 需求1 :  查询城市、省份、订单总额、订单总数----全量查询
 * 获取两部分数据
     * 1、dim_lagou_area  dim维表数据
     * 2、增量数据   lagou_trade_orders(HBase)
 * 进行计算
 *      1，2 统一到一起参与计算  sql
 *
 *      //把获取到的数据 转成flinktable中的临时表
 *      sql
 *
 */
object TotalCityOrder {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.enableCheckpointing(5000)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)

    //1、dim_lagou_area  dim维表数据
    val dimAreaStream: DataStream[(String, String)] = env.addSource(new ReadDimArea)
    //2、增量数据   lagou_trade_orders(HBase)
    val tradeOrderStream: DataStream[(String, String)] = env.addSource(new ReadTradeOrder)
//    tradeOrderStream.print()

    val areaStream: DataStream[DimArea] = dimAreaStream.map(x => {
      val arearId: Int = x._1.toInt
      val datas: Array[String] = x._2.split(",")
      val aname: String = datas(0).trim.toString
      val cid: Int = datas(1).trim.toInt
      val city: String = datas(2).trim.toString
      val proid: Int = datas(3).trim.toInt
      val province: String = datas(4).trim.toString
      DimArea(arearId, aname, cid, city, proid, province)
    })

//    areaStream.print()

    val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(env)
    tableEnv.createTemporaryView("dim_lagou_area",areaStream)

    /**
     * (1,370203,2020-06-28 18:14:01,2,0,1,2020-10-21 22:54:31,1,23a0b124546,2,2020-06-28 18:14:01,0.12,2,10468.0,0,0,98)
     * (1,370203,2020-06-28 18:14:01,2,0,1,2020-10-21 22:54:31,1,23a0b124546,2,2020-06-28 18:14:01,0.12,2,10468.0,0,0,98)
     * orderid
     * orderNo
     * userId
     * status
     * totalMoney
     * areaId
     */
    val orderStream: DataStream[TradeOrder] = tradeOrderStream.map(x => {
      val orderid: Int = x._1.toInt
      val datas: Array[String] = x._2.split(",")
      val orderNo: String = datas(7).trim.toString
      val userId: Int = datas(15).trim.toInt
      val status: Int = datas(11).toInt
      val totalMoney: Double = datas(12).toDouble
      val areaId: Int = datas(0).toInt
      TradeOrder(orderid, orderNo, userId, status, totalMoney, areaId)
    })

//    orderStream.print()

    tableEnv.createTemporaryView("lagou_orders",orderStream)

    val sql :String =
      """
        |select f.city,f.province,sum(f.qusum) as orderMoney, sum(f.qucount) as orderCount from
        |(select r.aname as qu,r.city as city,r.province as province,sum(k.totalMoney) as qusum,count(k.totalMoney) as qucount
        |from lagou_orders as k
        |inner join dim_lagou_area as r
        |on k.areaId = r.areaId
        |group by r.aname,r.city,r.province) as f
        |group by f.city,f.province
        |""".stripMargin


    val resultTable: Table = tableEnv.sqlQuery(sql)
//    resultTable.printSchema()
    val result: DataStream[(Boolean, Row)] = tableEnv.toRetractStream[Row](resultTable)

//    result.filter(x=>x._1==true).print()

    val value: DataStream[(Boolean, Row)] = result.filter(x => x._1 == true)
    value.addSink(new MongoSink)

//    result.filter(x=>x._1 == true).print()

    env.execute()

  }

}




































