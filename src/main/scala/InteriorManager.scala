import Operations._
import akka.actor.{Actor, Props}

class InteriorManager extends Actor {
  def receive: Receive = {
    case PrepareInterior => context.actorOf(Props[Painter], s"Painter") ! PaintWalls
    case WallsPainted => context.actorOf(Props[Fitter], s"Fitter") ! FitWindows
    case WindowsFitted =>
      context.parent ! InteriorPrepared
      context.stop(self)
  }
}
