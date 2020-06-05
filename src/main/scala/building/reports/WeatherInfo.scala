package building.reports

import framework.structures.RInfo
import building.structures.Weather

class WeatherInfo(memory: Int) extends RInfo[Weather](memory) {
  def %(weather: Weather): Double = Information.toArray.count(p => p == weather)

  override def resize(newMemory: Int): WeatherInfo = {Information.resize(newMemory); this}
  override def <<(element: Weather): WeatherInfo = {Information + element; this}
  override def <<<(elements: Array[Weather]): WeatherInfo = {Information ++ elements; this}
}
