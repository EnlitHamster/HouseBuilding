package building.framework

case object Predicts {
  def checkArg(expression: Boolean, template: String, args: Any*): Unit = if (!expression) throw new IllegalArgumentException(String.format(template, args))
  def checkNotNull(reference: Any, template: String, args: Any*): Any = if (reference == null) throw new NullPointerException else reference
}
