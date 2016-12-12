import akka.actor.{Actor, Props}
import akka.event.Logging

/**
  * Created by george on 12/11/16.
  */

object CollectorActor {

}

class CollectorActor() extends Actor{
  val log = Logging(context.system, this)
  def receive = {
    case "test" => {
      log.info("recieved test message")
    }
    case _      => {
      log.info("received unknown message")
    }
  }
}