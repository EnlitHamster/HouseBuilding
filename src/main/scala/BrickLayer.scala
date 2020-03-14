import Operation._
import akka.actor.Actor

class BrickLayer extends Actor {
  context.parent ! new Quantity(-Constants.Materials.Bricks)

  def receive: Receive = {
    case Delivered =>
      context.parent ! WallsBuilt
      context.stop(self)
  }
}
