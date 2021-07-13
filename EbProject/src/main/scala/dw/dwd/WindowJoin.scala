package dw.dwd

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.api.scala._

object WindowJoin {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val input1Stream: DataStream[(Int, Long)] = env.fromElements((1, 1999L), (1, 2001L)).assignAscendingTimestamps(_._2)
    val input2Stream: DataStream[(Int, Long)] = env.fromElements((1, 1001L), (1, 1002L), (1, 3999L)).assignAscendingTimestamps(_._2)

    input1Stream.join(input2Stream)
      .where(k=>k._1)
      .equalTo(k=>k._1)
      .window(TumblingEventTimeWindows.of(Time.seconds(2)))
      .apply { (e1,e2) => e1 + "...." + e2}
      .print()

    env.execute()
  }

}
