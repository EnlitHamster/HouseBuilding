package building

import akka.actor.Actor
import building.Operation._

class Fitter extends Actor {
  context.parent ! new Order(Material.Windows)

  def receive: Receive = {
    case d: Delivery =>
      if (d.Check) {
        context.parent ! WindowsFitted
        context.stop(self)
      } else context.parent ! new Order(d.Material)
  }
}
