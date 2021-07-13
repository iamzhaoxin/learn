package dim

import myutils.ConnHBase
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.hadoop.conf
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Put, Table}

class HBaseWriterSink extends RichSinkFunction[String]{
  var connection : Connection = _
  var hbTable : Table = _
  override def open(parameters: Configuration): Unit = {
    connection = new ConnHBase().connToHbase
    hbTable = connection.getTable(TableName.valueOf("dim_lagou_area"))
  }

  override def close(): Unit = {
    if(hbTable != null) {
      hbTable.close()
    }
    if (connection != null) {
      connection.close()
    }
  }

  def insertDimArea(hbTable: Table, value: String): Unit = {
    val infos: Array[String] = value.split(",")
    val areaId: String = infos(0).trim.toString
    val aname: String = infos(1).trim.toString
    val cid: String = infos(2).trim.toString
    val city: String = infos(3).trim.toString
    val proid: String = infos(4).trim.toString
    val province: String = infos(5).trim.toString

    val put = new Put(areaId.getBytes())
    put.addColumn("f1".getBytes(),"aname".getBytes(),aname.getBytes())
    put.addColumn("f1".getBytes(),"cid".getBytes(),cid.getBytes())
    put.addColumn("f1".getBytes(),"city".getBytes(),city.getBytes())
    put.addColumn("f1".getBytes(),"proId".getBytes(),proid.getBytes())
    put.addColumn("f1".getBytes(),"province".getBytes(),province.getBytes())

    hbTable.put(put)
  }

  override def invoke(value: String, context: SinkFunction.Context[_]): Unit = {
//    println(value)
    insertDimArea(hbTable,value)
  }

}
















