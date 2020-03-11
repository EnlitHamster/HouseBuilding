import Message.Operation._
import Message.Quantity
import akka.actor.Actor

class Painter extends Actor {
  context.parent ! new Quantity(Constants.Materials.Paint)

  def receive: Receive = {
    case Delivered =>
      context.parent ! WallsPainted
      context.stop(self)
  }
}
