trait Policy {
  // TODO: Add possibly exceptions registration system (like events)

  // Does the policy applies to a certain set of conditions
  def applies(conditions: Any*): Boolean
  // Acts in response to a report
  def choose(report: Report): PartialFunction[Any, Unit]
  // Acts in response to an exception
  def handle(exception: Exception): PartialFunction[Any, Unit]
}
