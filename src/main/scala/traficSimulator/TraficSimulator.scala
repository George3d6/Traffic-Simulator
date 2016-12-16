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
    val nothing = runSimmulation(timestamp, (time : Double) => {
      println(s"Now its the time $time")
    }, 1000, 500, 20, 0)

  }

  def runSimmulation(timestamp : Double, callback: (Double) => Unit, callFrequency : Double, speed : Long, maxIttNr : BigInt, ittNr : BigInt = 0): Double = {
    if(ittNr == 0) {
      println(s"Started at timestamp $timestamp")
    }
    callback(timestamp)
    Thread.sleep(speed)
    println(ittNr)
    println(maxIttNr)
    if(ittNr < maxIttNr) {
      runSimmulation(timestamp + callFrequency, callback, callFrequency, speed, maxIttNr, ittNr + 1)
    } else {
      println(s"Stopped at timestamp $timestamp")
      timestamp
    }
  }
}
