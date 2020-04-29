package building

import akka.actor.SupervisorStrategy._
import akka.actor.{ActorRef, OneForOneStrategy, Props}
import building.Operation._
import building.framework.AccountTaker
import building.framework.ReportPolicy.HandleResponse._
import building.policies.WorkerReportPolicy
import building.reports.WorkerReport

class FrameManager extends AccountTaker {
  val WeeklyReport: Int = 7
  var daysElapsed: Int = 0

  val workerPolicy: WorkerReportPolicy = new WorkerReportPolicy

  var brickLayer: ActorRef = _
  context.actorOf(Props[SitePreparer], s"SitePreparer")

  override def handle: Receive = {
    case SitePrepared => brickLayer = startSupervision[BrickLayer](s"BrickLayer")
    case WallsBuilt =>
      stopSupervision(brickLayer)
      context.parent ! FramePrepared
      context.stop(self)
    case dayPassed =>
      daysElapsed += 1
      if (daysElapsed == WeeklyReport) request(brickLayer)
    case report: WorkerReport =>
      val Mult: Double = workerPolicy.handle().apply(report.getProgress) match {
        case d: Double => d
        case NoHandle => 1.0
      }
      println(s"New multiplier: " + Mult)
      brickLayer ! Mult
    case q: Order => context.parent.forward(q)
  }

  // Exception management
  override val supervisorStrategy: OneForOneStrategy = OneForOneStrategy() {
    case _: BadWeatherException => Restart
  }
}
