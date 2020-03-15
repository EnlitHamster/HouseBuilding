import Operation._
import akka.actor.Actor

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
