package traficSimulator

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * Created by george on 12/18/16.
  */
object KafkaMessenger {

  val props = new Properties()
  val retires : AnyRef = Int.box(3)
  val batchSize : AnyRef = Int.box(16384)
  val lingerMs : AnyRef = Int.box(100)
  val bufferMemory : AnyRef = Int.box(33554432)
  props.put("bootstrap.servers", "localhost:9092")
  props.put("acks", "all")
  props.put("retries", retires)
  props.put("batch.size", batchSize)
  props.put("linger.ms", lingerMs)
  props.put("buffer.memory",  bufferMemory)
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  val producer = new KafkaProducer[String, String](props)

  def sendToKafka(carId : BigInt, message : String) : Unit= {
    producer.send(new ProducerRecord[String, String]("test", carId.toString, message))
  }

  def close() : Unit = {
    producer.close()
  }

}

