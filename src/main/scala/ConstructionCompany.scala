import Message.Operation._
import Message.Quantity
import akka.actor.SupervisorStrategy._
import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props}

class ConstructionCompany extends Actor {
  val MaterialManager: ActorRef = context.actorOf(Props[MaterialManager], s"MaterialManager")

  def receive: Receive = {
    //-------------------
    // BUILDING OPERATIONS
    //-------------------

    case BuildHouse =>
      MaterialManager ! new Quantity(100)
      context.become(awaitDelivery)
    case FramePrepared =>
      context.actorOf(Props[InteriorManager], s"InteriorManager") ! PrepareInterior
      context.actorOf(Props[ExteriorManager], s"ExteriorManager") ! PrepareExterior
    case InteriorPrepared | ExteriorPrepared => context.become(awaitLast)

    //--------------------
    // MATERIAL OPERATIONS
    //--------------------

    case q: Quantity => MaterialManager.forward(q)
  }

  def awaitDelivery: Receive = {
    case Delivered =>
      context.become(receive)
      context.actorOf(Props[FrameManager], s"FrameManager") ! PrepareFrame

    //--------------------
    // MATERIAL OPERATIONS
    //--------------------

    case q: Quantity => MaterialManager.forward(q)
  }

  // Receive status for parallel operations
  def awaitLast: Receive = {
    case InteriorPrepared | ExteriorPrepared =>
      println(s"House built")
      context.stop(self)

    //--------------------
    // MATERIAL OPERATIONS
    //--------------------

    case q: Quantity => MaterialManager.forward(q)
  }

  // Exception management
  override val supervisorStrategy: OneForOneStrategy = OneForOneStrategy() {
    case _: InsufficientMaterialsException =>
      MaterialManager ! new Quantity(100)
      Resume
    case _: BadWeatherException => Restart
  }
}
