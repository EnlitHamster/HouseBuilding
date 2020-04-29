package building.framework

abstract class ExceptionPolicy {
  // Acts in response to an exception
  def handle(exception: Exception): PartialFunction[Unit, Any]
}
