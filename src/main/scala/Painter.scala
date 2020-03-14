import Operation._
import akka.actor.Actor

class Painter extends Actor {
  context.parent ! new Quantity(-Constants.Materials.Paint)

  def receive: Receive = {
    case Delivered =>
      context.parent ! WallsPainted
      context.stop(self)
  }
}
