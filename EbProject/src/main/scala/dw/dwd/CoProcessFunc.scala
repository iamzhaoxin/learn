package dw.dwd

import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.scala.typeutils.Types
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.co.KeyedCoProcessFunction
import org.apache.flink.streaming.api.scala.{DataStream, KeyedStream, OutputTag, StreamExecutionEnvironment}
import org.apache.flink.util.Collector
import org.apache.flink.api.scala._

object CoProcessFunc {
  case class OrderEvent(orderId:String,eventType:String,eventTime:Long)
  case class PayEvent(orderId:String,eventType:String,eventTime:Long)
  val unmatchedOrders = new OutputTag[String]("unmatched-orders")
  val unmatchedPays = new OutputTag[String]("unmatched-pays")
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val orderStream: KeyedStream[OrderEvent, String] = env.fromElements(
      OrderEvent("order_1", "pay", 2000L),
      OrderEvent("order_2", "pay", 5000L),
      OrderEvent("order_3", "pay", 6000L)
    )
      .assignAscendingTimestamps(_.eventTime)
      .keyBy(_.orderId)

    val payStream: KeyedStream[PayEvent, String] = env.fromElements(
      PayEvent("order_1", "weixin", 7000L),
      PayEvent("order_2", "weixin", 8000L),
      PayEvent("order_4", "weixin", 9000L)
    )
      .assignAscendingTimestamps(_.eventTime)
      .keyBy(_.orderId)

    val processed: DataStream[String] = orderStream.connect(payStream).process(new MyConFunc)
    processed.print()
    processed.getSideOutput(unmatchedOrders).print()
    processed.getSideOutput(unmatchedPays).print()


    env.execute()
  }

  //keyBy...相同的key会进到同一个流（分区）中处理
  //valuestate
  class MyConFunc extends KeyedCoProcessFunction[String,OrderEvent,PayEvent,String] {
    lazy private val orderState : ValueState[OrderEvent] = getRuntimeContext.getState(new ValueStateDescriptor[OrderEvent]("orderState",Types.of[OrderEvent]))
    lazy private val payState : ValueState[PayEvent] = getRuntimeContext.getState(new ValueStateDescriptor[PayEvent]("payState",Types.of[PayEvent]))
    override def processElement1(value: OrderEvent, ctx: KeyedCoProcessFunction[String, OrderEvent, PayEvent, String]#Context, out: Collector[String]): Unit = {
      val pay = payState.value()
      if (pay != null) {
        payState.clear()
        out.collect("订单id为：" + pay.orderId + "的两条流对账成功")
      } else {
        /**
         * 如果pay中不存在数据，说明对应的pay（第二个流中的数据）没有到来，把value值更新到状态（第一个流的）中
         * 并且定义一个5分钟的定时器，到时候再匹配，如果还没有匹配上，说明需要报警
         */
        orderState.update(value)
        ctx.timerService().registerEventTimeTimer(value.eventTime + 5000)
      }
    }

    override def processElement2(value: PayEvent, ctx: KeyedCoProcessFunction[String, OrderEvent, PayEvent, String]#Context, out: Collector[String]): Unit = {
      val order = orderState.value()
      if(order != null) {
        orderState.clear()
        out.collect("订单id为：" + order.orderId + "的两条流对账成功")
      } else {
        payState.update(value)
        ctx.timerService().registerEventTimeTimer(value.eventTime + 5000)
      }
    }

    override def onTimer(timestamp: Long, ctx: KeyedCoProcessFunction[String, OrderEvent, PayEvent, String]#OnTimerContext, out: Collector[String]): Unit = {
      if(orderState.value != null) {
        ctx.output(unmatchedOrders,s"订单id为：${orderState.value().orderId}的两条流对账失败")
        orderState.clear()
      }
      if(payState.value() != null) {
        ctx.output(unmatchedOrders,s"订单id为：${payState.value().orderId}的两条流对账失败")
        payState.clear()
      }
    }
  }

}
