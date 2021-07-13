package dw.dws

import java.text.SimpleDateFormat
import java.util

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import modes.{CityOrder, TableObject}
import myutils.{ConnHBase, SourceKafka}
import org.apache.flink.addons.hbase.TableInputFormat
import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.api.java.tuple
import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.configuration.Configuration
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.scala.{DataStream, KeyedStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.util.Collector
import org.apache.hadoop.hbase.client.{Connection, HTable, Result, Scan}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{Cell, TableName}


/**
 * 需求2：每隔5分钟统计最近1小时内的订单交易情况，要求显示城市、省份、交易总金额、订单总数---增量统计
 */
object OrderStatistics {
  def main(args: Array[String]): Unit = {
    val envSet: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    //    ExecutionEnvironment.
        val envStream: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
//    val envStream: StreamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(config = new Configuration())
    envStream.enableCheckpointing(1000)
    envStream.setStateBackend(new FsStateBackend("file:\\\tmp\\lucas"))


    //    val timeout: Long = envStream.getConfig.getTaskCancellationTimeout
    //    val sdf = new SimpleDateFormat("yyyy-MM-dd:HH:hh:ss")
    //    val str1: String = sdf.format(timeout)
    //    print(".....timeOut:" + timeout)

    //获取两部分数据源
    //    dim_lagou_area //维度表
    val hbaseData: DataSet[tuple.Tuple2[String, String]] = envSet.createInput(new TableInputFormat[tuple.Tuple2[String, String]] {
      override def configure(parameters: Configuration): Unit = {
        val conn: Connection = new ConnHBase().connToHbase
        table = classOf[HTable].cast(conn.getTable(TableName.valueOf("dim_lagou_area")))
        scan = new Scan()
        scan.addFamily(Bytes.toBytes("f1"))
      }

      override def getScanner: Scan = {
        scan
      }

      override def getTableName: String = {
        "dim_lagou_area"
      }

      override def mapResultToTuple(r: Result): tuple.Tuple2[String, String] = {
        val rowKey: String = Bytes.toString(r.getRow)
        val sb = new StringBuffer()
        for (cell: Cell <- r.rawCells()) {
          val value: String = Bytes.toString(cell.getValueArray, cell.getValueOffset, cell.getValueLength)
          sb.append(value).append(",")
        }

        val valueString: String = sb.replace(sb.length() - 1, sb.length(), "").toString
        val tuple2 = new tuple.Tuple2[String, String]()
        tuple2.setField(rowKey, 0)
        tuple2.setField(valueString, 1)
        tuple2
      }
    })

//    hbaseData.print()

    val areaList: List[tuple.Tuple2[String, String]] = hbaseData.collect().toList


    //    lagou_trade_order
    //从kafka中获取增量数据
    val kafkaConsumer: FlinkKafkaConsumer[String] = new SourceKafka().getKafkaSource("test")
    kafkaConsumer.setStartFromEarliest()
    val dataSourceStream: DataStream[String] = envStream.addSource(kafkaConsumer)
    //    dataSourceStream.print()
    val dataSourceMapped: DataStream[TableObject] = dataSourceStream.map(x => {

      val objects = new util.ArrayList[TableObject]()
      try {

        val jsonObject: JSONObject = JSON.parseObject(x)
        var database: AnyRef = ""
        var table: AnyRef = ""
        var typeInfo: AnyRef = ""
        var array: JSONArray = null
        if (jsonObject != null) {
          database = jsonObject.get("database")
          table = jsonObject.get("table")
          typeInfo = jsonObject.get("type")
          array = jsonObject.getJSONArray("data")
        }


        if (array != null) {
          array.forEach(x => {
            objects.add(TableObject(database.toString, table.toString, typeInfo.toString, x.toString))
          })
        } else {
          objects.add(TableObject("", "", "", ""))
        }
      } catch {
        case e: Exception => {
          e.printStackTrace()
          objects.add(TableObject("", "", "", ""))
        }
      }
      objects.get(0)
    })

    val filteredData: DataStream[TableObject] = dataSourceMapped.filter(x => {
      x.tableName.toString.equals("lagou_trade_orders")
    })

    val orderInfo: DataStream[TradeOrder] = filteredData.map(x => {
      val order: TradeOrder = JSON.parseObject(x.dataInfo, classOf[TradeOrder])
      order
    })

    orderInfo.print()

    val keyed: KeyedStream[TradeOrder, Int] = orderInfo.keyBy(_.areaId)

    val value: DataStream[(String, (Double, Int))] = keyed.map(x => {
      var str: String = null
      var money: Double = 0L
      var count: Int = 0

      //地域 areaList
      for (area <- areaList) {
        if (area.f0.equals(x.areaId.toString)) {
          money += x.totalMoney
          count += 1
          str = area.f1
        }
      }
      val strs: Array[String] = str.split(",") //市南区,370200,青岛市,370000,山东省
      //((城市-省份）,(money,count))
      (strs(2).toString + "-" + strs(4).toString, (money, count))
    })

    val result: DataStream[(CityOrder, Long)] = value.keyBy(_._1)
      .timeWindow(Time.seconds(60 * 60), Time.minutes(1))
      .aggregate(new MyAggFunc(), new MyWindowFunc())

    result.print()


    //    result.addSink(new MySinkToRedis())

    envStream.execute()
  }

  class MyAggFunc extends AggregateFunction[(String, (Double, Int)), (Double, Long), (Double, Long)] {
    override def createAccumulator(): (Double, Long) = {
      (0, 0L)
    }

    override def add(value: (String, (Double, Int)), accumulator: (Double, Long)): (Double, Long) = {
      (accumulator._1 + value._2._1, accumulator._2 + value._2._2)
    }

    override def getResult(accumulator: (Double, Long)): (Double, Long) = {
      accumulator
    }

    override def merge(a: (Double, Long), b: (Double, Long)): (Double, Long) = {
      (a._1 + b._1, a._2 + b._2)
    }
  }

  class MyWindowFunc extends WindowFunction[(Double, Long), (CityOrder, Long), String, TimeWindow] {
    override def apply(
                        key: String,
                        window: TimeWindow,
                        input: Iterable[(Double, Long)],
                        out: Collector[(CityOrder, Long)]): Unit = {
      val info: (Double, Long) = input.iterator.next()
      val totalMoney: Double = info._1
      val totalCount: Long = info._2
      val strs: Array[String] = key.split("-")
      val city: String = strs(0)
      val province: String = strs(1)
      val sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss")

      print("...start:" + sdf.format(window.getStart) + "...end:" + sdf.format(window.getEnd))
      out.collect(CityOrder(city, province, totalMoney, totalCount.toInt), window.getEnd)
    }
  }

}
