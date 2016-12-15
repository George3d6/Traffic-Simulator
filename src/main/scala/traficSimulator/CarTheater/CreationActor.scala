package traficSimulator.CarTheater

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import traficSimulator.MapAPI.{Point, RouteApiWrapper}

import scala.util.Random

/**
  * Created by george on 12/11/16.
  */

object CreationActor {
  def props(carsHolder : ActorRef): Props
  = Props(new CreationActor(carsHolder))
}

class CreationActor(private val carsHolder : ActorRef) extends Actor{
  val log = Logging(context.system, this)

  def receive = {
    case creationTimestamp : BigInt => {
      val carId = generateUniqueId
      val carProps = Props(classOf[CarActor],
          RouteApiWrapper.returnRoute(
          RouteApiWrapper.getRandomPoint,
          RouteApiWrapper.getRandomPoint
        ),
        creationTimestamp,
        carId
      )
      val car : ActorRef= context.actorOf(carProps)
      log.info(s"Added car with id $carId into the system" +
        s" at timestmp $creationTimestamp")
      carsHolder ! (car, carId)
    }
    case _      => {
      log.info("received unknown instruction")
    }
  }

  private def generateUniqueId() : BigInt = {
    Random.nextInt
  }
}
