package myutils

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, HConstants}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory}

/**
 * 和HBase建立连接connection
 */
class ConnHBase {
  def connToHbase: Connection = {
    val conf: Configuration = HBaseConfiguration.create()
    //    conf.set("hbase.zookeeper.quorum","hadoop3,hadoop4,hadoop5")
    conf.set("hbase.zookeeper.quorum", "linux121,linux123")
    conf.set("hbase.zookeeper.property.clientPort", "2181")
    conf.setInt(HConstants.HBASE_CLIENT_OPERATION_TIMEOUT, 30000)
    conf.setInt(HConstants.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD, 30000)
    val connection = ConnectionFactory.createConnection(conf)
    connection
  }

}
