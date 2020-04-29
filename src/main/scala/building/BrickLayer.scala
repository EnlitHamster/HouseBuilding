package building

import building.structures.Operation._
import building.framework.structures.NumInfo
import building.framework.{AccountGiver, Report}
import building.reports.{WeatherInfo, WorkerReport}
import building.structures.Weather._
import building.structures.{Delivery, Material, Order}

import scala.util.Random

class BrickLayer extends AccountGiver {
  val Memory: Int = 28

  val Progress: NumInfo = new NumInfo(Memory) << 0.0
  val DaysWeather: WeatherInfo = new WeatherInfo(Memory)

  var multiplier: Double = 1.0;

  handle(adjustMultiplier, receiveDelivery)

  Supervisor ! Order(Material.Bricks)

  override def generateReport: Report = WorkerReport(Progress, DaysWeather)
  def adjustMultiplier: Receive = {case d: Double => multiplier = d}

  def receiveDelivery: Receive = {
    case d: Delivery =>
      if (d.Check) {
        println(s"Started BrickLayer construction")
        while (Progress.>> < 100) {
          var weather: Weather = null
          Random.nextInt(10) match {
            case r if 0 to 5 contains r => weather = Sunny
            case r if 6 to 7 contains r => weather = Rainy
            case _ => weather = Stormy
          }
          DaysWeather << weather
          Progress + (weather.id * multiplier)
          println(Progress >>)
          Supervisor ! dayPassed
        }

        Supervisor ! WallsBuilt
        context.stop(self)
      } else Supervisor ! Order(d.Material)
  }
}
