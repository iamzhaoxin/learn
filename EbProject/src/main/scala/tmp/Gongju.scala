package tmp

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory}

class Gongju {
  def connToHbase:Connection = {
    val conf: Configuration = HBaseConfiguration.create()
    //    conf.set("hbase.zookeeper.quorum","hadoop3,hadoop4,hadoop5")
    conf.set("hbase.zookeeper.quorum", "linux121,linux123")
    conf.set("hbase.zookeeper.property.clientPort", "2181")
    val connection:Connection = ConnectionFactory.createConnection(conf)
    connection
  }

}
