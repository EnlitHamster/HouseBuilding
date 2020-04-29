package building.structures

object Weather extends Enumeration {
  type Weather = Value
  val Sunny: Weather = Weather(2)
  val Rainy: Weather = Weather(1)
  val Stormy: Weather = Weather(0)
}
