package building.framework

import akka.actor.ActorRef
import building.framework.structures.Message

import scala.language.postfixOps

trait AccountGiver extends AccountableActor {
  val Supervisor: ActorRef = context.parent

  // Adding a preReceive operation to the Accountable actor
  preReceives += {case Message.Report => signal(generateReport)}

  def signal(report: Report): Unit = Supervisor ! report
  def escalate(exception: Exception): Unit = throw exception
  def generateReport: Report
}
