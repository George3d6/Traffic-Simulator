package traficSimulator

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

import akka.actor.{ActorSystem, Props}
import traficSimulator.CarTheater.{CarHolderActor, CollectorActor, CreationActor}

object TraficSimulator {
  def main(args: Array[String])= {

    var timestamp : Long = System.currentTimeMillis()

    val traficSimulatorActorSystem = ActorSystem("TrafficSimulatorSystem")

    val carHolder = traficSimulatorActorSystem.actorOf(Props[
      CarHolderActor])

    val creator = traficSimulatorActorSystem.actorOf(Props(classOf[
      CreationActor], carHolder))

    val collector = traficSimulatorActorSystem.actorOf(Props[
      CollectorActor])

    creator ! (32424000 : BigInt)
    creator ! (3123423 : BigInt)

    val ses : ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    //The loops basically represents the simulation advancing in time
    //YES, the timestamp should have arguably be wrapped in a monad but I don't care so much right now
    val run = ses.scheduleAtFixedRate(() => {
      carHolder ! (500 : BigInt)
      timestamp = timestamp + 500
    }, 0, 1000, TimeUnit.MICROSECONDS)

  }
}
