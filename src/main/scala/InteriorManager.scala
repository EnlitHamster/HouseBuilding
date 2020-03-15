import Operation._
import akka.actor.{Actor, Props}

class InteriorManager extends Actor {
  context.actorOf(Props[Painter], s"Painter")

  def receive: Receive = {
    case WallsPainted => context.actorOf(Props[Fitter], s"Fitter")
    case WindowsFitted =>
      context.parent ! InteriorPrepared
      context.stop(self)
    case q: Order => context.parent.forward(q)
  }
}
