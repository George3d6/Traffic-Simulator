package traficSimulator.CarTheater

import traficSimulator.MapAPI.Point
import akka.actor.Actor
import akka.event.Logging
/**
  * Created by george on 12/11/16.
  */



class CollectorActor() extends Actor{
  val log = Logging(context.system, this)
  def receive = {
    case (point : Point, speed : Double) => {
      log.info("Received the point $point and the median speed of $speed")
    }
    case _      => {
      log.info("Collector received unknown message")
    }
  }
}