package building

import framework.structures.NumInfo
import framework.{AccountGiver, Report}
import building.reports.{WeatherInfo, WorkerReport}
import building.structures.Operation._
import building.structures.Weather._
import building.structures.{Delivery, Material, Order, Weather}

import scala.language.postfixOps
import scala.util.Random

case object Work

class BrickLayer extends AccountGiver {
  val Memory: Int = 28

  val Progress: NumInfo = new NumInfo(Memory) << 0.0
  val DaysWeather: WeatherInfo = new WeatherInfo(Memory)

  var multiplier: Double = 1.0

  handle(adjustMultiplier)
  handle(receiveDelivery)
  handle(workCycle)

  override def start(): Unit = {super.start(); Supervisor ! Order(Material.Bricks)}
  override def generateReport: Report = WorkerReport(Progress, DaysWeather)
  def adjustMultiplier: Receive = {case d: Double => multiplier = d}

  def receiveDelivery: Receive = {
    case d: Delivery =>
      if (d.Check) self ! Work
      else Supervisor ! Order(d.Material)
  }

  def workCycle: Receive = {
    case Work =>
      var weather: Weather = null
      Random.nextInt(10) match {
        case r if 0 to 5 contains r => weather = Sunny
        case r if 6 to 7 contains r => weather = Rainy
        case _ => weather = Stormy
      }
      DaysWeather << weather
      Progress + (weather.Progress * multiplier)
      Supervisor ! dayPassed

      if (Progress.>> < 100) self ! Work
      else Supervisor ! WallsBuilt
  }
}
