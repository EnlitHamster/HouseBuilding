import Message.Operation._
import Message.Quantity
import akka.actor.Actor

import scala.util.Random

class ExteriorManager extends Actor {
  context.parent ! new Quantity(Constants.Materials.Concrete)
  context.parent ! new Quantity(Constants.Materials.Logs)

  def receive: Receive = {
    case Delivered => context.become(awaitDelivery)
  }

  def awaitDelivery: Receive = {
    case Delivered =>
      if (Random.nextInt(99) > 79) throw BadWeatherException()
      context.parent ! ExteriorPrepared
      context.stop(self)
  }
}
