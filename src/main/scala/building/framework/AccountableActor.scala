package building.framework

import akka.actor.Actor

trait AccountableActor extends Actor {
  protected var preReceives: List[Receive] = List[Receive]()

  def handle: Receive

  override final def receive: Receive = new Receive {
    def isDefinedAt(x: Any): Boolean = preReceives.exists(p => p.isDefinedAt(x)) || handle.isDefinedAt(x)
    def apply(x: Any): Unit = {
      preReceives.foreach(preReceive => if (preReceive.isDefinedAt(x)) preReceive.apply(x))
      if (handle.isDefinedAt(x)) handle.apply(x)
    }
  }
}
