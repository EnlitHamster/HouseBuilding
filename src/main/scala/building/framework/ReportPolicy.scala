package building.framework

object ReportPolicy {
  object HandleResponse extends Enumeration {
    type HandleResponse = Value
    val NoHandle: ReportPolicy.HandleResponse.Value = Value
  }
}

abstract class ReportPolicy {
  // Acts in response to a report
  def handle(): PartialFunction[Any, Any]
}
