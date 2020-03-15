import Operation._
import akka.actor.SupervisorStrategy._
import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props}

import scala.language.postfixOps

class ConstructionCompany extends Actor {
  var client: ActorRef = _
  val MaterialManager: ActorRef = context.actorOf(Props[MaterialManager], s"MaterialManager")
  context.become(awaitCheck)
  MaterialManager ! new Order(Material.Batch)

  def receive: Receive = {
    //--------------------
    // BUILDING OPERATIONS
    //--------------------
    case FramePrepared =>
      context.actorOf(Props[InteriorManager], s"InteriorManager")
      context.actorOf(Props[ExteriorManager], s"ExteriorManager")
    case InteriorPrepared | ExteriorPrepared => context.become(awaitLast)

    //----------------------------
    // MATERIAL & SETUP OPERATIONS
    //----------------------------
    case q: Order => MaterialManager.forward(q)
    case BuildHouse => client = sender()
  }

  // Waiting for first materials to be shipped
  def awaitCheck: Receive = {
    case d: Delivery =>
      if (d.Check) {
        context.become(receive)
        context.actorOf(Props[FrameManager], s"FrameManager")
      } else MaterialManager ! new Order(d.Material)
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
    case q: Order => MaterialManager.forward(q)
  }

  // Exception management
  override val supervisorStrategy: OneForOneStrategy = OneForOneStrategy() {
    case _: InsufficientMaterialsException =>
      MaterialManager ! new Order(Material.Batch)
      Resume
    case _: BadWeatherException => Restart
  }
}
