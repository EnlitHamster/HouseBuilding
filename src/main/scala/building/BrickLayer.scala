package building

import building.Operation._
import building.framework.structures.NumInfo
import building.framework.{AccountGiver, Report}
import building.reports.{WeatherInfo, WorkerReport}

import scala.util.Random

class BrickLayer extends AccountGiver {
  val Memory: Int = 28

  val Progress: NumInfo = new NumInfo(Memory) << 0.0
  val DaysWeather: WeatherInfo = new WeatherInfo(Memory)

  var multiplier: Double = 1.0;

  Supervisor ! new Order(Material.Bricks)

  override def receiveMsg: Receive = {
    case d: Double => multiplier = d
    case d: Delivery =>
      if (d.Check) {
        println(s"Started BrickLayer construction")
        while (Progress.>> < 100) {
          var weather: Weather = null
          Random.nextInt(10) match {
            case r if 0 to 5 contains r => weather = Weather.SUNNY
            case r if 6 to 7 contains r => weather = Weather.RAINY
            case _ => weather = Weather.STORMY
          }
          DaysWeather << weather
          Progress + (weather.progress * multiplier)
          println(Progress >>)
          Supervisor ! dayPassed
        }

        Supervisor ! WallsBuilt
        context.stop(self)
      } else Supervisor ! new Order(d.Material)
  }

  override def generateReport: Report = WorkerReport(Progress, DaysWeather)
}
