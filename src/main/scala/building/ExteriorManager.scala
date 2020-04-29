package building

import akka.actor.Actor
import building.structures.Operation._
import building.structures.Order
import building.structures.Material._

import scala.util.Random

class ExteriorManager extends Actor {
  context.parent ! Order(Concrete)
  context.parent ! Order(Logs)

  def receive: Receive = {
    case d: structures.Delivery =>
      if (d.Check) context.become(awaitDelivery)
      else context.parent ! Order(d.Material)
  }

  def awaitDelivery: Receive = {
    case d: structures.Delivery =>
      if (d.Check) {
        if (Random.nextInt(99) > 79) throw BadWeatherException()
        context.parent ! ExteriorPrepared
        context.stop(self)
      } else context.parent ! Order(d.Material)
  }
}
