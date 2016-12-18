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
    case creationTimestamp : Double  => {
      val carId = generateUniqueId
      val carProps : Props = Props(classOf[CarActor],
          RouteApiWrapper.returnRoute(
          RouteApiWrapper.getRandomPoint,
          RouteApiWrapper.getRandomPoint
        ),
        creationTimestamp,
        carId
      )
      carsHolder ! (carProps, carId)
    }
    case _      => {
      log.info("received unknown instruction")
    }
  }

  private def generateUniqueId() : BigInt = {
    Math.abs(Random.nextInt)
  }
}
