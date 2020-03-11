import Message.Operation._
import Message.Quantity
import akka.actor.Actor

class BrickLayer extends Actor {
  context.parent ! new Quantity(Constants.Materials.Bricks)

  def receive: Receive = {
    case Delivered =>
      context.parent ! WallsBuilt
      context.stop(self)
  }
}
