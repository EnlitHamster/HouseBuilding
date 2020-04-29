package building

import akka.actor.Actor
import building.structures.Operation._
import building.structures.{Delivery, Material, Order}

class Painter extends Actor {
  context.parent ! Order(Material.Paint)

  def receive: Receive = {
    case d: Delivery =>
      if (d.Check) {
        context.parent ! WallsPainted
        context.stop(self)
      } else context.parent ! Order(d.Material)
  }
}
