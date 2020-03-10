import Message.Operation._
import akka.actor.Actor

class Fitter extends Actor {
  def receive: Receive = {
    case FitWindows =>
      // TODO: possibility of InsufficientMaterialsException
      context.parent ! WindowsFitted
      context.stop(self)
  }
}
