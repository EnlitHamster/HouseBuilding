import Operations._
import akka.actor.Actor

class BrickLayer extends Actor {
  def receive: Receive = {
    case BuildWalls =>
      // TODO: possibility of InsufficientMaterialsException
      context.parent ! WallsBuilt
      context.stop(self)
  }
}
