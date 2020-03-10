import Message.Operation._
import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, OneForOneStrategy, Props}

class ConstructionCompany extends Actor {
  def receive: Receive = {
    //-------------------
    // BUILDING OPERATIONS
    //-------------------

    case BuildHouse => context.actorOf(Props[FrameManager], s"FrameManager") ! PrepareFrame
    case FramePrepared =>
      context.actorOf(Props[InteriorManager], s"InteriorManager") ! PrepareInterior
      context.actorOf(Props[ExteriorManager], s"ExteriorManager") ! PrepareExterior
    case InteriorPrepared | ExteriorPrepared => context.become(awaitLast)
  }

  // Receive status for parallel operations
  def awaitLast: Receive = {
    case InteriorPrepared | ExteriorPrepared =>
      println(s"House built")
      context.stop(self)
  }

  // Exception management
  override val supervisorStrategy: OneForOneStrategy = OneForOneStrategy() {
    case _: BadWeatherException => Restart
  }
}
