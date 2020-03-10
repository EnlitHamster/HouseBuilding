import Operation._
import akka.actor.Actor

class Painter extends Actor {
  def receive: Receive = {
    case PaintWalls =>
      // TODO: possibility of InsufficientMaterialsException - requires materials
      context.parent ! WindowsFitted
      context.stop(self)
  }
}
