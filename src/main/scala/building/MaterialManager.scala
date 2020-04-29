package building

import akka.actor.Actor
import building.structures.{Material, Order}

class MaterialManager extends Actor {

  // TODO Add a blocking point when handling InsufficientMaterialsExceptions to avoid handling the same multiple times
  // [1] Introduce a TimeLived variable that increments at every storage operation and resets when throwing an
  //     building.InsufficientMaterialsException and checking it whenever trying to throw an building.InsufficientMaterialsException
  //     ATTENTION: This is a form of synchronization that should be avoided. Another method should be preferred.
  //     Throwing the exception multiple times is not acceptable though, because this Actor MUST be held accountable
  //     for knowing if it has already thrown such an exception

  // Synchronization can be achieved through this.synchronized {...}

  var waitingOrder = true
  var materials = 0

  override def receive: Receive = {
    case o: Order =>
      if (o.Material.equals(Material.Batch)) {
        materials += o.Material.id
        waitingOrder = false
        sender() ! structures.Delivery(Check = true, o.Material)
      } else if (o.Material.id > materials) this.synchronized {
        sender() ! structures.Delivery(Check = false, o.Material)
        if (!waitingOrder) {
          waitingOrder = true
          throw InsufficientMaterialsException()
        }
      } else {
        materials -= o.Material.id
        sender() ! structures.Delivery(Check = true, o.Material)
      }
  }
}
