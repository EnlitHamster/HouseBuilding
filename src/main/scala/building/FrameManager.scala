package building

import akka.actor.SupervisorStrategy._
import akka.actor.{ActorRef, OneForOneStrategy, Props}
import building.structures.Operation._
import building.framework.AccountTaker
import building.framework.ReportPolicy.HandleResponse._
import building.policies.WorkerReportPolicy
import building.reports.WorkerReport
import building.structures.{Operation, Order}

class FrameManager extends AccountTaker {
  val WeeklyReport: Int = 7
  var daysElapsed: Int = 0
  val workerPolicy: WorkerReportPolicy = new WorkerReportPolicy

  var brickLayer: ActorRef = startSupervision[BrickLayer](s"BrickLayer")

  handle(forwardOrder)
  handle(handleWorkerReport)
  handle(startBrickLaying)
  handle(workFinished)
  val DayPassedID: Int = handle(dayPassed)

  context.actorOf(Props[SitePreparer], s"SitePreparer")

  def forwardOrder: Receive = {case q: Order => context.parent.forward(q)}
  def startBrickLaying: Receive = {case SitePrepared => start(brickLayer)}

  def workFinished: Receive = {
    case WallsBuilt =>
      stopSupervision(brickLayer)
      context.parent ! FramePrepared
      context.stop(self)
  }

  def dayPassed: Receive = {
    case Operation.dayPassed =>
      daysElapsed += 1
      if (daysElapsed == WeeklyReport) {
        println(s"--" + self.path.name + "-- Request")
        request(brickLayer)
        daysElapsed = 0
      }
  }

  def handleWorkerReport: Receive = {
    case report: WorkerReport =>
      val Mult: Double = workerPolicy.handle().apply(report) match {
        case d: Double => d
        case NoHandle => println(s"--" + self.path.name + "-- NoHandle"); 1.0
        case StopHandle => unhandle(DayPassedID); println(s"--" + self.path.name + "-- StopHandle"); 1.0
      }

      println(s"--" + self.path.name + "-- New multiplier: " + Mult)
      brickLayer ! Mult
  }

  // Exception management
  override val supervisorStrategy: OneForOneStrategy = OneForOneStrategy() {
    case _: BadWeatherException => Restart
  }
}
