package building

import akka.actor.Actor
import building.structures.Operation._
import building.structures.{Delivery, Material, Order}

class Fitter extends Actor {
  context.parent ! Order(Material.Windows)

  def receive: Receive = {
    case d: Delivery =>
      if (d.Check) {
        context.parent ! WindowsFitted
        context.stop(self)
      } else context.parent ! Order(d.Material)
  }
}
