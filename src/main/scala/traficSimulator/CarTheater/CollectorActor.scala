package traficSimulator.CarTheater

import traficSimulator.MapAPI.Point
import akka.actor.Actor
import akka.event.Logging
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import traficSimulator.KafkaMessenger

/**
  * Created by george on 12/11/16.
  */



class CollectorActor() extends Actor{
  val log = Logging(context.system, this)
  def receive = {
    case (point : Point, speed : Double, timestamp : Double, carId : BigInt) => {
      log.info(s"Received the point $point and the median speed of $speed")
      val lat = point.latitude
      val lon = point.longitude
      val fmt = DateTimeFormat.forPattern("yyyy-MM-dd/hh:mm:ss:SSS")
      val date : String = fmt.print( new DateTime(timestamp.toLong) )
      KafkaMessenger.sendToKafka(carId,
        s""" {"latitude": "$lat", "longitude" : "$lon",
           | "speed" : "$speed", "date" : $date "id" : "$carId"} """.stripMargin)
    }
    case _      => {
      log.info("Collector received unknown message")
    }
  }
}