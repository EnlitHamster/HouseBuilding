import Message.Operation._
import Message.Quantity
import akka.actor.SupervisorStrategy._
import akka.actor.{Actor, OneForOneStrategy, Props}

class FrameManager extends Actor {
  context.actorOf(Props[SitePreparer], s"SitePreparer")

  def receive: Receive = {
    case SitePrepared => context.actorOf(Props[BrickLayer], s"BrickLayer")
    case WallsBuilt =>
      context.parent ! FramePrepared
      context.stop(self)
    case q: Quantity => context.parent.forward(q)
  }

  // Exception management
  override val supervisorStrategy: OneForOneStrategy = OneForOneStrategy() {
    case _: BadWeatherException => Restart
  }
}
