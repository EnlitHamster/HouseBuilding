package building.reports

import building.Weather
import building.framework.structures.RInfo

class WeatherInfo(memory: Int) extends RInfo[Weather](memory) {
  def %(weather: Weather): Double = Information.toArray.count(p => p == weather)

  override def resize(newMemory: Int): WeatherInfo = {Information.resize(newMemory); this}
  override def <<(element: Weather): WeatherInfo = {Information + element; this}
  override def <<<(elements: Array[Weather]): WeatherInfo = {Information ++ elements; this}
}
