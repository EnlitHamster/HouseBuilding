package building.reports

import building.framework.Report
import building.framework.structures.NumInfo
import building.structures.Weather._

import scala.language.postfixOps

case class WorkerReport(Progress: NumInfo, Weather: WeatherInfo) extends Report {
  def percentSunny: Double = Weather % Sunny
  def percentRainy: Double = Weather % Rainy
  def percentStormy: Double = Weather % Stormy
  def daysElapsed: Int = Weather length
  def getProgress: Double = Progress >>
}
