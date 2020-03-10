import Message.Operation._
import Message.Quantity
import akka.actor.Actor

class Painter extends Actor {
  def receive: Receive = {
    case PaintWalls =>
      context.parent ! new Quantity(Constants.Materials.Paint)
      context.become(awaitDelivery)
  }

  def awaitDelivery: Receive = {
    case Delivered =>
      context.parent ! WallsPainted
      context.stop(self)
  }
}
