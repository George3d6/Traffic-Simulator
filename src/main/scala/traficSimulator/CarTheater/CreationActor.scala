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

  var lastStamp : Double = 0

  var innactiveCars : Map[Double, (BigInt, Point)] = Map()

  val log = Logging(context.system, this)

  def receive = {
    case creationTimestamp : Double  => {
      lastStamp = creationTimestamp
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

    case ("restart",creationTimestamp)  => {

      if( (Math.random*10000).toInt == 11) {
        println("Restarting a car")
        innactiveCars.foreach((pair)=>{
          val (id : BigInt, point : Point) = pair._2
          val restartCarProps : Props = Props(classOf[CarActor],
            RouteApiWrapper.returnRoute(
              point,
              RouteApiWrapper.getRandomPoint
            ),
            creationTimestamp,
            id
          )
          carsHolder ! (restartCarProps, id)
        })
        innactiveCars = Map()
      }

    }

    case ("restart", at : Point, carId : BigInt) => {
      println(s"Got a new innactive car, now I have ${innactiveCars.size}")
      innactiveCars = innactiveCars + (lastStamp + carId.toDouble -> (carId, at))
    }

    case _      => {
      log.info("received unknown instruction")
    }
  }

  private def generateUniqueId() : BigInt = {
    Math.abs(Random.nextInt)
  }
}
