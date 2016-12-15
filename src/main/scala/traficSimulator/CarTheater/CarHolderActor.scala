package traficSimulator.CarTheater

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging

/**
  * Created by george on 12/15/16.
  */

object CarHolderActor {
  def props(): Props
  = Props(new CarHolderActor())
}

class CarHolderActor() extends Actor {
  val log = Logging(context.system, this)
  var carList : Map[BigInt, ActorRef] = Map()

  def receive = {
    case (car : ActorRef, carId : BigInt) => {
      carList = carList + (carId -> car)
      log.info("Got car")
    }
    case timestamp : BigInt => {
      log.info("Showing off my cars")
      carList.foreach(carEntity => {
        val carId = carEntity._1
        val car = carEntity._2
        log.info("At current timestamp I hold $car with id $carId")
      })
    }
    case _      => {
      log.info("Holder received unknown message")
    }
  }

}
