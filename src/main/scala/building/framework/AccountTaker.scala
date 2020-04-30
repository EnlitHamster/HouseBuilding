package building.framework

import akka.actor.{Actor, ActorRef, Props}
import building.framework.structures.Message

import scala.collection.mutable
import scala.language.postfixOps
import scala.reflect.ClassTag

trait AccountTaker extends AccountableActor {
  // List of Policies the Actor from which to choose when a situation surges
  /*var policies: List[Policy] = Nil

  def handle(situation: Report*): Unit
  def addPolicy(policy: Policy): Unit = if (!hasPolicyType[policy.type]) policies = policy :: policies
  def removePolicy(policy: Policy): Unit = if (policies contains policy) policies = policies filter (x => !x.equals(policy))
  def hasPolicyType[T <: Any: ClassTag]: Boolean = policies exists (x => x.isInstanceOf[T])*/
  var supervised: List[ActorRef] = Nil
  val Messages: mutable.Queue[Any] = mutable.Queue[Any]()

  def startSupervision[A <: AccountGiver : ClassTag](name: String): ActorRef = {
    val Ref: ActorRef = context.actorOf(Props[A], name)
    supervised ::= Ref
    Ref
  }

  def stopSupervision(ref: ActorRef): Unit = supervised = supervised filter (_ != ref)

  def request(aGiver: ActorRef): Boolean = {
    if (supervised contains aGiver) {
      aGiver ! Message.Report
      true
    } else false
  }
}
