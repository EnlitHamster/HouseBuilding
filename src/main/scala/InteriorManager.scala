import Message.Operation._
import Message.Quantity
import akka.actor.{Actor, Props}

class InteriorManager extends Actor {
  context.actorOf(Props[Painter], s"Painter")

  def receive: Receive = {
    case WallsPainted => context.actorOf(Props[Fitter], s"Fitter")
    case WindowsFitted =>
      context.parent ! InteriorPrepared
      context.stop(self)
    case q: Quantity => context.parent.forward(q)
  }
}
