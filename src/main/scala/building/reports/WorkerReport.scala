package building.reports

import building.framework.Report
import building.framework.structures.NumInfo

case class WorkerReport(Progress: NumInfo, Weather: WeatherInfo) extends Report {
  def percentSunny: Double = Weather % building.Weather.SUNNY
  def percentRainy: Double = Weather % building.Weather.RAINY
  def percentStormy: Double = Weather % building.Weather.STORMY
  def daysElapsed: Int = Weather length
  def getProgress: Double = Progress >>
}
