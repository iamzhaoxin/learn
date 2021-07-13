package tmp

import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.{ProcessWindowFunction, WindowFunction}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * descriptions:
 * author: kolton
 * date: 2021 - 06 - 24 18:01
 */
object WindowTest {
    def main(args: Array[String]): Unit = {
        val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        // val inputStreamFromFile: DataStream[String] = environment.readTextFile("data/sensor/sensor.txt")
        val inputStreamFromFile: DataStream[String] = environment.socketTextStream("lagou01", 9999)

        val mapDataStream: DataStream[SensorReading] = inputStreamFromFile
            .map(
                (data: String) => {
                    val dataArray: Array[String] = data.trim.split(",")
                    SensorReading(dataArray(0), dataArray(1).trim.toLong, dataArray(2).trim.toDouble)
                }
            )
        mapDataStream.keyBy(_.id)
            // .window( EventTimeSessionWindows.withGap(Time.minutes(1)) ) //会话窗口
            // .timeWindow(Time.minutes(1)) //滚动窗口
            // .window(TumblingProcessingTimeWindows.of(Time.days(1), Time.hours(-8))) //滚动窗口
            // .countWindow(10) // 滚动计数窗口
            // .countWindow(10,5) // 滑动技术窗口
            .timeWindow(Time.seconds(15), Time.seconds(5))
            .apply(new WindowFunction[SensorReading, Int, String, TimeWindow] {
                override def apply(key: String, window: TimeWindow, input: Iterable[SensorReading], out: Collector[Int]): Unit = {
                    out.collect(1)
                }
            })
//            .process(new ProcessWindowFunction[SensorReading, (Long,Int), Tuple, TimeWindow] {
//                override def process(key: Tuple, context: Context, elements: Iterable[SensorReading], out: Collector[(Long, Int)]): Unit = {
//                    out.collect((window.getStart, input.size))
//
//                }
//            })
            //.reduce(new MyReduce)

//        resultStream.print("result")
        environment.execute("window api test")
    }
}

// 自定义全窗口函数
/*
class MyWindowFun extends WindowFunction[SensorReading, (Long, Int), Tuple, TimeWindow]{
    override def apply(key: Tuple, window: TimeWindow, input: Iterable[SensorReading], out: Collector[(Long, Int)]): Unit = {
        out.collect((window.getStart, input.size))
    }
}*/
