package building.policies

import building.framework.ReportPolicy
import building.framework.ReportPolicy.HandleResponse._
import building.reports.WorkerReport
import building.structures.Weather._

object WorkerReportPolicy {
  val BaseProgressExpectation: Double = 6 * Sunny.id
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

  override def handle(): PartialFunction[Any, Any] = {
    case report: WorkerReport if report.getProgress < expectedProgress => expectedProgress = getExpected(DefExpMult); DefMult
    case report: WorkerReport if report.getProgress > expectedProgress => expectedProgress = getExpected(OverExpMult); OverMult
    case _: WorkerReport => expectedProgress = BaseProgressExpectation; OkMult
    case _ => NoHandle
  }
}