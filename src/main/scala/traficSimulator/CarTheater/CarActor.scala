package traficSimulator.CarTheater

import scala.util.Random
import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import traficSimulator.MapAPI.{Calculator, Point}

object CarActor {
  val maxSpeed : Double = 60
  val minSpeed : Double = 0
  def props(route : Array[Point], timestampCreated: Double, carId : BigInt): Props
  = Props(new CarActor(route, timestampCreated, carId))
}

//The car actor receives its current positions from a SimulatorActor and sends it to a CollectorActor
class CarActor(route : Array[Point], timestampCreated : Double, carId : BigInt) extends Actor {
  val log = Logging(context.system, this)
  var total : Double = 0
  def receive = {
    //Only request a car can recieve its for its current position
    //This will be computed based on its internal state and the route it follows
    case ("get_position", timeInMs : Double, collector : ActorRef) => {

      if(at.latitude == route(0).latitude &&
        at.longitude == route(0).longitude) {

        val distanceTraveled = ((timeInMs - lastTime)/1000) * speed
        val (gotAt, goTo, position) = Calculator.getNextLocation(route, at, to, distanceTraveled)
        at = gotAt
        to = goTo
        collector ! (at, speed, timeInMs, "started", carId)
        lastTime = timeInMs
        total += distanceTraveled

      } else {
        computeMeanSpeed()
        val distanceTraveled = ((timeInMs - lastTime)/1000) * speed
        val (gotAt, goTo, position) = Calculator.getNextLocation(route, at, to, distanceTraveled)
        at = gotAt
        to = goTo
        collector ! (at, speed, timeInMs, position, carId)
        lastTime = timeInMs

        if(position == "stop") {
          log.info(s"Distance driven when I stoped was: ${total.toString}") //<= To prove formula works
          context.parent ! ("restart", at, carId )
          context.stop(self)
        } else {
          total += distanceTraveled
        }

      }

    }
    case _ => {
      log.info("Unknown message recieved by car with id: ", carId)
    }
  }

  private var lastTime = timestampCreated

  private var at = route(0)
  private var to = 1

  //Speed of the car (in m/s), this can change
  private var speed : Double = 16

  //Aggresivity determined how much a driver will be willing to increase his speed
  private val aggressivity : Double = 0.2

  //Varriance, determined by both road conditions and the driver himself, determines
  //how much the shifts in speed are
  private val variance : Double = 0.02

  //Compute a random number which represents the average speed over a certain duration of time
  //Currrently it doesn't take any external factors into consideration
  private def computeMeanSpeed() = {
    val randomFactor : Double = variance*Random.nextGaussian()
    val newSpeed = speed + randomFactor + aggressivity*(CarActor.maxSpeed/speed)
    speed = if(newSpeed < CarActor.minSpeed) {
      0
    } else if(newSpeed > CarActor.maxSpeed) {
      newSpeed - variance*speed
    } else {
      newSpeed
    }
  }

  //Max "reasonable" speed == 60 m/s
  //Min "reasonable" speed == 0 m/s

}