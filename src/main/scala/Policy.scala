trait Policy {
  def choose(report: Report): PartialFunction[Any, Unit]
}
