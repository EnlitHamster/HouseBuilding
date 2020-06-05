package building.policies

import framework.ReportPolicy
import framework.ReportPolicy.HandleResponse._
import building.reports.WorkerReport
import building.structures.Weather._

object WorkerReportPolicy {
  val BaseProgressExpectation: Double = 6 * Sunny.Progress
  val OkMult: Double = 1.0
  val DefMult: Double = 1.5
  val OverMult: Double = 0.75
  val DefExpMult: Double = 1.25
  val OverExpMult: Double = 0.83

  def getExpected(multiplier: Double): Double = BaseProgressExpectation * multiplier
}

class WorkerReportPolicy extends ReportPolicy {
  import WorkerReportPolicy._

  // Setting the initial expected progress to 5 full workdays
  var expectedProgress: Double = BaseProgressExpectation
  var lastProgress: Double = 0.0

  override def handle(): PartialFunction[Any, Any] = {
    case report: WorkerReport if report.getProgress - lastProgress < expectedProgress =>
      expectedProgress = getExpected(DefExpMult); lastProgress = report.getProgress; DefMult
    case report: WorkerReport if report.getProgress - lastProgress > expectedProgress =>
      expectedProgress = getExpected(OverExpMult); lastProgress = report.getProgress; OverMult
    case report: WorkerReport =>
      expectedProgress = BaseProgressExpectation; lastProgress = report.getProgress; StopHandle
    case _ => NoHandle
  }
}