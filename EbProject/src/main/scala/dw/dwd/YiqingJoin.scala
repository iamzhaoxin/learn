package dw.dwd

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.api.scala._
import tmp.MyRedisSink


object YiqingJoin {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val input1Stream: DataStream[(Int, Long,Int)] = env.fromElements((1, 1999L ,1000), (1, 2001L,2000)).assignAscendingTimestamps(_._2)
    val input2Stream: DataStream[(Int, Long,Int)] = env.fromElements((2, 1001L,1000), (3, 1002L,1000), (4, 3999L,4000)).assignAscendingTimestamps(_._2)

    input1Stream.join(input2Stream)
      .where(k=>k._3)
      .equalTo(k=>k._3)
      .window(TumblingEventTimeWindows.of(Time.hours(2)))
      .apply { (e1,e2) => e1 + "...." + e2}
      .print()


    env.execute()
  }
}
