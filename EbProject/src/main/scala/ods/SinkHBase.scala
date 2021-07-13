package ods

import java.util

import com.alibaba.fastjson.{JSON, JSONObject}
import modes.{AreaInfo, DataInfo, TableObject}
import myutils.ConnHBase
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.hadoop.conf.Configuration
import org.apache.flink.configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Delete, Put, Table}

class SinkHBase extends RichSinkFunction[util.ArrayList[TableObject]]{
  var connection : Connection = _
  var hbTable : Table = _
  /**
   * 实例化 HBase
   * connection
   * hbTable
   * @param parameters
   */
  override def open(parameters: configuration.Configuration): Unit = {
    connection = new ConnHBase().connToHbase
    //    connection.
    hbTable = connection.getTable(TableName.valueOf("lagou_area"))
  }

  override def close(): Unit = {
    if(hbTable != null) {
      hbTable.close()
    }
    if (connection != null) {
      connection.close()
    }
  }

  /**
   * 每来一条数据，会执行一次
   * @param value
   * @param context
   */
  override def invoke(value: util.ArrayList[TableObject], context: SinkFunction.Context[_]): Unit = {
    value.forEach(x => {
      println(x.toString)
      val database: String = x.database
      val tableName: String = x.tableName
      val typeInfo: String = x.typeInfo

      if(database.equalsIgnoreCase("dwshow") && tableName.equalsIgnoreCase("lagou_trade_orders")) {
        hbTable = connection.getTable(TableName.valueOf("lagou_trade_orders"))
        if(typeInfo.equalsIgnoreCase("insert")) {
          value.forEach(x => {
            val info: DataInfo = JSON.parseObject(x.dataInfo, classOf[DataInfo])
            insertTradeOrders(hbTable,info)
          })
        } else if(typeInfo.equalsIgnoreCase("update")) {

        } else if (typeInfo.equalsIgnoreCase("delete")) {

        }
      }

      if(database.equalsIgnoreCase("dwshow") && tableName.equalsIgnoreCase("lagou_area")) {
        if(typeInfo.equalsIgnoreCase("insert")) {
          System.out.println("enter");
          value.forEach(x => {
            val info: AreaInfo = JSON.parseObject(x.dataInfo, classOf[AreaInfo])
            insertArea(hbTable,info)
          })
        } else if(typeInfo.equalsIgnoreCase("update")) {
          value.forEach(x => {
            val info: AreaInfo = JSON.parseObject(x.dataInfo, classOf[AreaInfo])
            insertArea(hbTable,info)
          })
        } else if (typeInfo.equalsIgnoreCase("delete")) {
          value.forEach(x => {
            val info: AreaInfo = JSON.parseObject(x.dataInfo, classOf[AreaInfo])
            deleteArea(hbTable,info)
          })

        }
      }

    })
  }

  //lagou_area省份城市区域表，根据id删除数据
  def deleteArea(hbTable: Table, areaInfo: AreaInfo): Unit = {
    val delete = new Delete(areaInfo.id.getBytes)
    hbTable.delete(delete)
  }

  def insertArea(hbTable: Table, areaInfo: AreaInfo) : Unit = {
    println(areaInfo.toString)
    val put = new Put(areaInfo.id.getBytes())
    put.addColumn("f1".getBytes(), "name".getBytes(), areaInfo.name.getBytes())
    put.addColumn("f1".getBytes(), "pid".getBytes(), areaInfo.pid.getBytes())
    put.addColumn("f1".getBytes(), "sname".getBytes(), areaInfo.sname.getBytes())
    put.addColumn("f1".getBytes(), "level".getBytes(), areaInfo.level.getBytes())
    put.addColumn("f1".getBytes(), "citycode".getBytes(), areaInfo.citycode.getBytes())
    put.addColumn("f1".getBytes(), "yzcode".getBytes(), areaInfo.yzcode.getBytes())
    put.addColumn("f1".getBytes(), "mername".getBytes(), areaInfo.mername.getBytes())
    put.addColumn("f1".getBytes(), "lng".getBytes(), areaInfo.Lng.getBytes())
    put.addColumn("f1".getBytes(), "lat".getBytes(), areaInfo.Lat.getBytes())
    put.addColumn("f1".getBytes(), "pinyin".getBytes(), areaInfo.pinyin.getBytes())
    System.out.println("success!")
    hbTable.put(put)
  }
  def insertTradeOrders(hbTable: Table, dataInfo: DataInfo): Unit = {
    val put = new Put(dataInfo.orderId.getBytes)
    print("orderId: " + dataInfo.orderId)
    put.addColumn("f1".getBytes,"modifiedTime".getBytes,dataInfo.modifiedTime.getBytes())
    put.addColumn("f1".getBytes,"orderNo".getBytes,dataInfo.orderNo.getBytes())
    put.addColumn("f1".getBytes,"isPay".getBytes,dataInfo.isPay.getBytes())
    put.addColumn("f1".getBytes,"orderId".getBytes,dataInfo.orderId.getBytes())
    put.addColumn("f1".getBytes,"tradeSrc".getBytes,dataInfo.tradeSrc.getBytes())
    put.addColumn("f1".getBytes,"payTime".getBytes,dataInfo.payTime.getBytes())
    put.addColumn("f1".getBytes,"productMoney".getBytes,dataInfo.productMoney.getBytes())
    put.addColumn("f1".getBytes,"totalMoney".getBytes,dataInfo.totalMoney.getBytes())
    put.addColumn("f1".getBytes,"dataFlag".getBytes,dataInfo.dataFlag.getBytes())
    put.addColumn("f1".getBytes,"userId".getBytes,dataInfo.userId.getBytes())
    put.addColumn("f1".getBytes,"areaId".getBytes,dataInfo.areaId.getBytes())
    put.addColumn("f1".getBytes,"createTime".getBytes,dataInfo.createTime.getBytes())
    put.addColumn("f1".getBytes,"payMethod".getBytes,dataInfo.payMethod.getBytes())
    put.addColumn("f1".getBytes,"isRefund".getBytes,dataInfo.isRefund.getBytes())
    put.addColumn("f1".getBytes,"tradeType".getBytes,dataInfo.tradeType.getBytes())
    put.addColumn("f1".getBytes,"status".getBytes,dataInfo.status.getBytes())

    hbTable.put(put)
  }
}
