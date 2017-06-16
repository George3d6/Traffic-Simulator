package traficSimulator

import java.io.File

import com.typesafe.config.ConfigFactory

/**
  * Created by george on 12/15/16.
  */
object Config {
  private val config =  ConfigFactory.load()

  val port : String = config.getString("kafka.port")

  val topic : String = config.getString("kafka.topic")

  val retires : AnyRef = Int.box(config.getInt("kafka.retries"))
  val batchSize : AnyRef = Int.box(config.getInt("kafka.batchSize"))
  val lingerMs : AnyRef = Int.box(config.getInt("kafka.lingerMs"))
  val bufferMemory : AnyRef = Int.box(config.getInt("kafka.bufferMemory"))
}
