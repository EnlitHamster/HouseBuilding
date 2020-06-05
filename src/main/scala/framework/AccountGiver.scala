package framework

import akka.actor.ActorRef
import framework.structures.Message

import scala.language.postfixOps

trait AccountGiver extends AccountableActor {
  val Supervisor: ActorRef = context.parent

  // Adding a preReceive operation to the Accountable actor
  preReceives += (nPreReceives -> handleReport)
  preReceives += (nPreReceives + 1 -> awaitStart)
  val AwaitStartID: Int = nPreReceives + 1
  nPreReceives += 2

  private def handleReport: Receive = {case Message.Report => signal(generateReport)}
  private def awaitStart: Receive = {case Message.Start => start()}
  def signal(report: Report): Unit = Supervisor ! report
  def escalate(exception: Exception): Unit = throw exception
  def generateReport: Report
  def start(): Unit = preReceives -= AwaitStartID
}
