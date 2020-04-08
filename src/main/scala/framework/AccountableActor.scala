package framework

import akka.actor.Actor

import scala.language.postfixOps

abstract class AccountableActor extends Actor {
  // TODO: Limit to 1 policy per policy type
  // List of Policies the Actor from which to choose when a situation surges
  var policies: List[Policy] = Nil

  def handle(situation: List[Report]): Unit
  def addPolicy(policy: Policy): Unit = policies = policy :: policies
  def removePolicy(policy: Policy): Unit = if (policies contains policy) policies = policies filter (x => !x.equals(policy))
}
