package myutils

import akka.util.Helpers.Requiring

import java.util.Properties
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.connectors.kafka.internals.metrics.KafkaConsumerMetricConstants

import scala.tools.nsc.backend.jvm.BackendReporting.Invalid

class SourceKafka {
  def getKafkaSource(topicName: String) : FlinkKafkaConsumer[String] = {
    val props = new Properties()

    props.setProperty("bootstrap.servers","linux121:9092,linux123:9092"); //虚拟机
//    props.setProperty("bootstrap.servers","hadoop2:9092,hadoop3:9092,hadoop4:9092");//3,4
    props.setProperty("group.id","consumer-group2")
    props.setProperty("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer")
    props.setProperty("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer")
//    Invalid value earlist for configuration auto.offset.reset: String must be one of: latest, earliest, none
//    props.setProperty("auto.offset.reset","earliest")
    props.setProperty("auto.offset.reset","latest")

    val consumer = new FlinkKafkaConsumer[String](topicName, new SimpleStringSchema(), props)
    consumer.setStartFromEarliest();
    consumer
  }
}
