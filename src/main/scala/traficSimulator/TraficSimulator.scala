package traficSimulator

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

import akka.actor.{ActorSystem, Props}
import traficSimulator.CarTheater.{CarHolderActor, CollectorActor, CreationActor}

object TraficSimulator {
  def main(args: Array[String])= {

    //Get the current timestamp, we assume the start time of the simulation is whenever the program starts running
    //If you want to change the start time change this value
    val timestamp : Double = System.currentTimeMillis()

    /*
    *  Initialize the actor system and the three top level actors we use for this simulation
    *  the creator adds cars to the simulation, the collector pipes data to kafka
    *  and the holder is a router to the cars in order to update them with info about the simulation
     */
    val traficSimulatorActorSystem = ActorSystem("TrafficSimulatorSystem")

    val holder = traficSimulatorActorSystem.actorOf(Props[
      CarHolderActor], "holder")

    val creator = traficSimulatorActorSystem.actorOf(Props(classOf[
      CreationActor], holder), "creator")

    val collector = traficSimulatorActorSystem.actorOf(Props[
      CollectorActor], "collector")
    //

    KafkaProducer.sendToKafka
    creator ! timestamp
    creator ! timestamp
    creator ! timestamp
    creator ! timestamp
    creator ! timestamp
    creator ! timestamp
    creator ! timestamp
    //holder ! (3999 : BigInt)

    //The loops basically represents the simulation advancing in time
    //YES, the timestamp should have arguably be wrapped in a monad but I don't care that much right now
    //val nothing = runSimmulation(timestamp, (time : Double) => {
    //  println(s"Now its the time $time")
    //}, 1000, 500, 1, 0)

  }

  //Function used to simulate the advancement of time. Takes as argument: an unix timestamp (the beginning time),
  //Callback function to execute with each itteration (nothing is done with the callbacks return value)
  //By How much to update the timestamp with each iteration, Frequency at which to run a new iteration (in milliseconds),
  //Number of iterations and [at which itteration to start, defaults at 0]
  //Returns the ("simulated") timestamp at which it stops running
  def runSimmulation(timestamp : Double, callback: (Double) => Unit, timestampIncrease : Double,
                     speed : Long, maxIttNr : BigInt, ittNr : BigInt = 0): Double = {
    if(ittNr == 0) {
      println(s"Started at timestamp $timestamp")
    }
    callback(timestamp)
    Thread.sleep(speed)
    println(ittNr)
    println(maxIttNr)
    if(ittNr < maxIttNr) {
      runSimmulation(timestamp + timestampIncrease, callback, timestampIncrease, speed, maxIttNr, ittNr + 1)
    } else {
      println(s"Stopped at timestamp $timestamp")
      timestamp
    }
  }
}
