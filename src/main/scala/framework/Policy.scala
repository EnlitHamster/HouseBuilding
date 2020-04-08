package framework

trait Policy {
  // TODO: Add possibly exceptions registration system (like events)

  // Does the policy applies to a certain set of conditions
  def applies(conditions: Any*): Boolean
  // Acts in response to a report
  def handle(report: Report): PartialFunction[Unit, Any]
  // Acts in response to an exception
  def handle(exception: Exception): PartialFunction[Unit, Any]
}
