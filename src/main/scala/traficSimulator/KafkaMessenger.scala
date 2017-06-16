package traficSimulator

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * Created by george on 12/18/16.
  */
object KafkaMessenger {

  val props = new Properties()

  props.put("bootstrap.servers", s"localhost:${Config.port}")
  props.put("acks", "all")
  props.put("retries", Config.retires)
  props.put("batch.size", Config.batchSize)
  props.put("linger.ms", Config.lingerMs)
  props.put("buffer.memory",  Config.bufferMemory)
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  val producer = new KafkaProducer[String, String](props)

  def sendToKafka(carId : BigInt, message : String) : Unit= {
    producer.send(new ProducerRecord[String, String](Config.topic, carId.toString, message))
  }

  def close() : Unit = {
    producer.close()
  }

}

