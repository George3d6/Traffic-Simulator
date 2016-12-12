import akka.actor.{Actor, Props}
import akka.event.Logging

/**
  * Created by george on 12/11/16.
  */

object CreationActor {

}

class CreationActor() extends Actor{
  val log = Logging(context.system, this)
  def receive = {
    case creationTimestamp : BigInt => {
      val car = new CarActor()
    }
    case _      => {
      log.info("received unknown instruction")
    }
  }

  def generateUniqueId() : BigInt = {
    42
  }

  def generateRoute() : Array[Point] = {
    null
  }
}
