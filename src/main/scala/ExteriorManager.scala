import Operation._
import akka.actor.Actor

import scala.util.Random

class ExteriorManager extends Actor {
  context.parent ! new Quantity(-Constants.Materials.Concrete)
  context.parent ! new Quantity(-Constants.Materials.Logs)

  // TODO: Create Enum for Constants.Materials to handle parallel situations like this

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
