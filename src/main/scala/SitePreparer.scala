import Message.Operation._
import Message.Quantity
import akka.actor.Actor

import scala.util.Random

class SitePreparer extends Actor {
  def receive: Receive = {
    case PrepareSite =>
      context.parent ! new Quantity(Constants.Materials.Concrete)
      context.become(awaitDelivery)
  }

  def awaitDelivery: Receive = {
    case Delivered =>
      if (Random.nextInt(99) > 79) throw BadWeatherException(self, PrepareSite)
      context.parent ! SitePrepared
      context.stop(self)
  }
}
