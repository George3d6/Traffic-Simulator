package traficSimulator.CarTheater

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging

/**
  * Created by george on 12/15/16.
  */

object CarHolderActor {
  def props(collector : ActorRef): Props
  = Props(new CarHolderActor(collector : ActorRef))
}

class CarHolderActor(private val collector : ActorRef) extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case (carProps : Props, carId : BigInt) => {
      val car : ActorRef= context.actorOf(carProps, "car-" + carId)
     //log.info(s"Got car $car")
    }
    case timestamp : Double => {
      log.info("Showing off my cars")
      this.context.children.foreach((car) => {
        car ! ("get_position", timestamp, collector)
      })
    }
    case _      => {
      log.info("Holder received unknown message")
    }
  }

}
