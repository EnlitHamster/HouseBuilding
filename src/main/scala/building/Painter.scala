package building

import akka.actor.Actor
import building.Operation._

class Painter extends Actor {
  context.parent ! new Order(Material.Paint)

  def receive: Receive = {
    case d: Delivery =>
      if (d.Check) {
        context.parent ! WallsPainted
        context.stop(self)
      } else context.parent ! new Order(d.Material)
  }
}
