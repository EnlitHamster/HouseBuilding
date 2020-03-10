import Operations._
import akka.actor.Actor

class Painter extends Actor {
  def receive: Receive = {
    case PaintWalls =>
      // TODO: possibility of InsufficientMaterialsException
      context.parent ! WindowsFitted
      context.stop(self)
  }
}
