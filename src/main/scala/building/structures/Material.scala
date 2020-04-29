package building.structures

object Material extends Enumeration {
  type Material = Value
  val Batch: Material = Material(50)
  val Concrete: Material = Material(20)
  val Bricks: Material = Material(15)
  val Paint: Material = Material(5)
  val Windows: Material = Material(5)
  val Logs: Material = Material(20)
}
