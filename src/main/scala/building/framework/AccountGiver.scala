package building.framework

import akka.actor.ActorRef

trait AccountGiver extends AccountableActor {
  val Supervisor: ActorRef = context.parent

  def signal(report: Report): Unit = Supervisor ! report
  def escalate(exception: Exception): Unit = throw exception
  def generateReport: Report

  // Adding a preReceive operation to the Accountable actor
  preReceives ::= {case _: Message => signal(generateReport)}
}
