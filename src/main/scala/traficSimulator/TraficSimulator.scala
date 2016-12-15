package traficSimulator

import akka.actor.{ActorSystem, Props}
import traficSimulator.CarTheater.{CarHolderActor, CollectorActor, CreationActor}

object TraficSimulator {
  def main(args: Array[String]) = {

    val traficSimulatorActorSystem = ActorSystem("TrafficSimulatorSystem")

    val carHolder = traficSimulatorActorSystem.actorOf(Props[
      CarHolderActor])

    val creator = traficSimulatorActorSystem.actorOf(Props(classOf[
      CreationActor], carHolder))

    val collector = traficSimulatorActorSystem.actorOf(Props[
      CollectorActor])

    creator ! (32424000 : BigInt)
    creator ! (3123423 : BigInt)
    carHolder ! (3274000 : BigInt)
    carHolder ! (7403200 : BigInt)

  }
}
