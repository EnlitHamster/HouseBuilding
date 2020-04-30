package building.framework

import akka.actor.Actor

import scala.collection.mutable.ListBuffer

trait AccountableActor extends Actor {
  protected var preReceives: ListBuffer[Receive] = ListBuffer[Receive]()
  protected var handles: ListBuffer[Receive] = ListBuffer[Receive]()

  def handle(receive: Receive*): Unit = handles ++= receive

  override final def receive: Receive = new Receive {
    def isDefinedAt(x: Any): Boolean =
      preReceives.exists(p => p.isDefinedAt(x)) || handles.exists(p => p.isDefinedAt(x))

    def apply(x: Any): Unit = {
      preReceives.foreach(preReceive => if (preReceive.isDefinedAt(x)) preReceive.apply(x))
      handles.foreach(handle => if (handle.isDefinedAt(x)) handle.apply(x))
    }
  }
}