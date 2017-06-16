package traficSimulator.CarTheater

import akka.actor.{Actor, Props}
import akka.event.Logging
import traficSimulator.MapAPI.Point

/**
  * Created by george on 12/19/16.
  */
class SimulationActor extends Actor{
  val log = Logging(context.system, this)

  private val collector = context.actorOf(Props[
    CollectorActor], "collector")

  private val holder = context.actorOf(Props(classOf[
    CarHolderActor], collector), "holder")

  private val creator = context.actorOf(Props(classOf[
    CreationActor], holder), "creator")

  def receive = {
    case ("create_car", timestamp : Double) => {
      creator ! timestamp
    }

    case("send_messages", timestamp : Double) => {
      holder ! timestamp
      creator ! ("restart", timestamp)
    }

    case ("restart", at : Point, carId : BigInt) => {
      creator ! ("restart", at, carId)
    }

    case _      => {
        log.info("simulation received unknown instruction")
    }
  }
}
