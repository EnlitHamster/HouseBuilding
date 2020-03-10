import Message.Operation._
import Message.Quantity
import akka.actor.Actor

import scala.util.Random

class ExteriorManager extends Actor {
  def receive: Receive = {
    case PrepareExterior =>
      context.parent ! new Quantity(Constants.Materials.Concrete)
      context.parent ! new Quantity(Constants.Materials.Logs)
      context.become(awaitFirstDelivery)
  }

  def awaitFirstDelivery: Receive = {
    case Delivered => context.become(awaitSecondDelivery)
  }

  def awaitSecondDelivery: Receive = {
    case Delivered =>
      if (Random.nextInt(99) > 79) throw BadWeatherException(self, PrepareSite)
      context.parent ! ExteriorPrepared
      context.stop(self)
  }
}
