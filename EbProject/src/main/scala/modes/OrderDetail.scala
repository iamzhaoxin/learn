package modes

/**
 *
 * @param orderId 订单id
 * @param status 订单状态
 * @param orderCreateTime 订单创建时间
 * @param price 价钱
 */
case class OrderDetail(orderId:String, status:String, orderCreateTime:String, price:Double)
