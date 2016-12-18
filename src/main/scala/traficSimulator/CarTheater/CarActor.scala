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

  def receive = {
    //Only request a car can recieve its for its current position
    //This will be computed based on its internal state and the route it follows
    case ("get_position", timeInSec : Double, collector : ActorRef) => {
      computeMeanSpeed
      val distanceTraveled = timeInSec * speed
      val location: Point = Calculator.projectPoint(from, to, distanceTraveled)
      from = location
      collector ! (location, speed, carId)
    }
    case _ => {
      log.info("Unknown message recieved by car with id: ", carId)
    }
  }

  //The car has a point where the route begins and one where the route ends
  //These should remain constant throughout the "route"
  private val start : Point = route(0)
  private val finish : Point = route(route.length - 1)

  //At any given point a car is inbetween two points in our route
  //We shall call these points from, to. Their value can change
  //After information is recieved
  private var from : Point = start
  private var to : Point = route(1)

  //This is the point that was last send by compute point
  private var lastAt : Point = start

  //Speed of the car (in m/s), this can change
  private var speed : Double = 16

  //Aggresivity determined how much a driver will be willing to increase his speed
  private val aggressivity : Double = 2

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