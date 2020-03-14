import Operation._
import akka.actor.Actor

class MaterialManager extends Actor {
  var materials = 0
  override def receive: Receive = {
    case q: Quantity =>
      println(s"Order for ${q.Quantity}")
      if (q.Quantity < 0 && Math.abs(q.Quantity) > materials) throw InsufficientMaterialsException(sender(), q)
      else materials += q.Quantity
      sender() ! Delivered
  }
}
