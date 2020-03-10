import Operation._
import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, OneForOneStrategy, Props}

class FrameManager extends Actor {
  def receive: Receive = {
    case PrepareFrame => context.actorOf(Props[SitePreparer], s"SitePreparer") ! PrepareSite
    case SitePrepared => context.actorOf(Props[BrickLayer], s"BrickLayer") ! BuildWalls
    case WallsBuilt =>
      context.parent ! FramePrepared
      context.stop(self)
  }

  // Exception management
  override val supervisorStrategy: OneForOneStrategy = OneForOneStrategy() {
    case _: BadWeatherException => Restart
    case _: InsufficientMaterialsException => Restart
  }
}
