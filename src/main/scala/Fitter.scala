import Operation._
import akka.actor.Actor

class Fitter extends Actor {
  context.parent ! new Quantity(-Constants.Materials.Windows)

  def receive: Receive = {
    case Delivered =>
      context.parent ! WindowsFitted
      context.stop(self)
    case NotDelivered => context.parent ! new Quantity(-Constants.Materials.Windows)
  }
}
