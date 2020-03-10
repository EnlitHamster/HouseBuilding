import Message.Operation._
import Message.Quantity
import akka.actor.Actor

class MaterialManager extends Actor {
  var materials = 0
  override def receive: Receive = {
    case q: Quantity =>
      if (q.Quantity < 0 && Math.abs(q.Quantity) > materials) throw InsufficientMaterialsException(self, Order)
      else materials += q.Quantity
      q.From ! Delivered
  }
}
