import Message.Operation._
import Message.Quantity
import akka.actor.Actor

class Fitter extends Actor {
  def receive: Receive = {
    case FitWindows =>
      context.parent ! new Quantity(Constants.Materials.Windows)
      context.become(awaitDelivery)
  }

  def awaitDelivery: Receive = {
    case Delivered =>
      context.parent ! WindowsFitted
      context.stop(self)
  }
}
