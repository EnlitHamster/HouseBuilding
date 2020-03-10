import Message.Operation._
import Message.Quantity
import akka.actor.SupervisorStrategy._
import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props}

class FrameManager extends Actor {
  val sitePreparer: ActorRef = context.actorOf(Props[SitePreparer], s"SitePreparer")

  def receive: Receive = {
    case PrepareFrame => sitePreparer ! PrepareSite
    case SitePrepared => context.actorOf(Props[BrickLayer], s"BrickLayer") ! BuildWalls
    case WallsBuilt =>
      context.parent ! FramePrepared
      context.stop(self)
    case q: Quantity => context.parent.forward(q)
  }

  // Exception management
  override val supervisorStrategy: OneForOneStrategy = OneForOneStrategy() {
    case e: OperationException =>
      e.Sender ! e.Op
      Resume
  }
}
