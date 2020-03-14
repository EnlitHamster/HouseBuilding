import Operation._
import akka.actor.Actor

class MaterialManager extends Actor {

  // TODO Add a blocking point when handling InsufficientMaterialsExceptions to avoid handling the same multiple times
  // [1] Introduce a TimeLived variable that increments at every storage operation and resets when throwing an
  //     InsufficientMaterialsException and checking it whenever trying to throw an InsufficientMaterialsException
  //     ATTENTION: This is a form of synchronization that should be avoided. Another method should be preferred.
  //     Throwing the exception multiple times is not acceptable though, because this Actor MUST be held accountable
  //     for knowing if it has already thrown such an exception

  // Synchronization can be achieved through this.synchronized {...}

  var tl = 0          // Time lived since last InsufficientMaterialsException
  var materials = 0

  override def receive: Receive = {
    case q: Quantity =>
      println(s"Order for ${q.Quantity}")
      if (q.Quantity < 0 && Math.abs(q.Quantity) > materials) this.synchronized {
        tl = 0;
        sender() ! NotDelivered
        throw InsufficientMaterialsException(sender(), q)
      } else {
        tl += 1
        materials += q.Quantity
        sender() ! Delivered
      }
  }
}
