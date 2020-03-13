import Message.Operation._
import Message.Quantity
import akka.actor.SupervisorStrategy._
import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class ConstructionCompany extends Actor {
  var client: ActorRef = _
  val MaterialManager: ActorRef = context.actorOf(Props[MaterialManager], s"MaterialManager")
  MaterialManager ! new Quantity(50)

  def receive: Receive = {
    //--------------------
    // BUILDING OPERATIONS
    //--------------------

    case Delivered => context.actorOf(Props[FrameManager], s"FrameManager")
    case FramePrepared =>
      context.actorOf(Props[InteriorManager], s"InteriorManager")
      context.actorOf(Props[ExteriorManager], s"ExteriorManager")
    case InteriorPrepared | ExteriorPrepared => context.become(awaitLast)

    //----------------------------
    // MATERIAL & SETUP OPERATIONS
    //----------------------------

    case q: Quantity => MaterialManager.forward(q)
    case BuildHouse => client = sender()
  }

  // Receive status for parallel operations
  def awaitLast: Receive = {
    case InteriorPrepared | ExteriorPrepared =>
      if (client != null) client ! HouseBuilt else println(s"House Built (no client)")
      context.stop(MaterialManager)
      context.stop(self)

    //--------------------
    // MATERIAL OPERATIONS
    //--------------------

    case q: Quantity => MaterialManager.forward(q)
  }

  // Exception management
  override val supervisorStrategy: OneForOneStrategy = OneForOneStrategy() {
    case _: InsufficientMaterialsException =>
      if (handle(MaterialManager, new Quantity(100)) == Delivered) Resume else Escalate
    case _: BadWeatherException => Restart
  }

  def handle(actor: ActorRef, msg: Any, time: FiniteDuration = 5 seconds): Any = {
    implicit val Timeout: Timeout = time
    val Ask = actor ? msg
    Await.result(Ask, Timeout.duration)
  }
}
