package dim

import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import org.apache.flink.types.Row

object AreaDetailInfo {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.enableCheckpointing(5000)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)

    /**
     * (110107,010-39.9056-3-116.223-中国,北京,北京市,石景山区-石景山区-110100-Shijingshan-石景山-100043)
     * (110116,010-40.316-3-116.632-中国,北京,北京市,怀柔区-怀柔区-110100-Huairou-怀柔-101400)
     */
    val data: DataStream[(String, String)] = env.addSource(new HBaseReader)

//    data.print()

//获取几个必要的字段id name pid
    val dataStream: DataStream[AreaDetail] = data.map(x => {
      val id: Int = x._1.toInt
      val datas: Array[String] = x._2.split("-")
      val name: String = datas(5).trim
      val pid: Int = datas(6).trim.toInt

      AreaDetail(id, name, pid)
    })

    //转成 地区id,地区的名字，城市的id，城市的名字， 省份的id，省份的名字
    //FlinkTable api
    val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(env)


    //临时表
    tableEnv.createTemporaryView("lagou_area",dataStream)

    //sql -- 生成 区、市、省三级的明细宽表
    val sql : String =
      """
        |select a.id as areaid,a.name as aname,a.pid as cid,b.name as city, c.id as proid,c.name as province
        |from lagou_area as a
        |inner join lagou_area as b on a.pid = b.id
        |inner join lagou_area as c on b.pid = c.id
        |""".stripMargin


    val areaTable: Table = tableEnv.sqlQuery(sql)

    val resultStream: DataStream[String] = tableEnv.toRetractStream[Row](areaTable).map(x => {
      val row: Row = x._2
      val areaId: String = row.getField(0).toString
      val aname: String = row.getField(1).toString
      val cid: String = row.getField(2).toString
      val city: String = row.getField(3).toString
      val proid: String = row.getField(4).toString
      val province: String = row.getField(5).toString
      areaId + "," + aname + "," + cid + "," + city + "," + proid + "," + province
    })
    resultStream.addSink(new HBaseWriterSink)
//    resultStream.print()

    env.execute()
  }
}
