import Operation._
import akka.actor.Actor

class BrickLayer extends Actor {
  context.parent ! new Order(Material.Bricks)

  def receive: Receive = {
    case d: Delivery =>
      if (d.Check) {
        context.parent ! WallsBuilt
        context.stop(self)
      } else context.parent ! new Order(d.Material)
  }
}
