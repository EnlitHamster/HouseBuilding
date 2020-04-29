package building

import akka.actor.Actor
import building.Operation._

import scala.util.Random

class ExteriorManager extends Actor {
  context.parent ! new Order(Material.Concrete)
  context.parent ! new Order(Material.Logs)

  def receive: Receive = {
    case d: Delivery =>
      if (d.Check) context.become(awaitDelivery)
      else context.parent ! new Order(d.Material)
  }

  def awaitDelivery: Receive = {
    case d: Delivery =>
      if (d.Check) {
        if (Random.nextInt(99) > 79) throw BadWeatherException()
        context.parent ! ExteriorPrepared
        context.stop(self)
      } else context.parent ! new Order(d.Material)
  }
}
