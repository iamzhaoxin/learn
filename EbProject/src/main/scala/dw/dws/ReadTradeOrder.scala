package dw.dws

import java.util

import myutils.ConnHBase
import org.apache.flink.configuration
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.hadoop.conf
import org.apache.hadoop.hbase.{Cell, HBaseConfiguration, HConstants, TableName}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Result, ResultScanner, Scan, Table}
import org.apache.hadoop.hbase.util.Bytes

import scala.collection.JavaConverters._

class ReadTradeOrder extends RichSourceFunction[(String,String)]{
  private var conn :Connection = null;
  private var table : Table = null;
  private var scan : Scan = null;
  var flag = false

  override def open(parameters: configuration.Configuration): Unit = {
//    val tableName: TableName = TableName.valueOf("lucs")
//    val tableName: TableName = TableName.valueOf("orders")
    val tableName: TableName = TableName.valueOf("lagou_trade_orders")
    val cf1 :String = "f1"
    conn = new ConnHBase().connToHbase
    table = conn.getTable(tableName)
    scan = new Scan()
    scan.addFamily(Bytes.toBytes(cf1))

  }



  override def run(ctx: SourceFunction.SourceContext[(String, String)]): Unit = {
    if(!flag) {
      val rs: ResultScanner = table.getScanner(scan)
      val iterator: util.Iterator[Result] = rs.iterator()
      while(iterator.hasNext) {
        val result: Result = iterator.next()
        val rowKey: String = Bytes.toString(result.getRow)
        val buffer = new StringBuffer()
        for(cell: Cell <- result.listCells().asScala) {
          val value: String = Bytes.toString(cell.getValueArray, cell.getValueOffset, cell.getValueLength)
          buffer.append(value).append(",")
        }
        val valueString: String = buffer.replace(buffer.length() - 1, buffer.length(), "").toString
//        println("--" + valueString)
        ctx.collect(rowKey,valueString)
      }
    }
  }

  override def cancel(): Unit = {
    flag = true;
  }

  override def close(): Unit = {
    try{
      if(table != null) {
        table.close()
      }
      if(conn != null) {
        conn.close()
      }
    }catch {
      case e : Exception => println(e.getMessage)
    }
  }
}
