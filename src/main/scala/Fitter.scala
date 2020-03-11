import Message.Operation._
import Message.Quantity
import akka.actor.Actor

class Fitter extends Actor {
  context.parent ! new Quantity(Constants.Materials.Windows)

  def receive: Receive = {
    case Delivered =>
      context.parent ! WindowsFitted
      context.stop(self)
  }
}
