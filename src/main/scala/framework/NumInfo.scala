package framework

class NumInfo(memory: Int) extends RInfo[Double](memory) {
  def avg(): Double = Information.toArray.sum / Information.length
  def max(): Double = Information.toArray.max
  def min(): Double = Information.toArray.min
  def mid(): Double = Information.toArray(Information.length / 2)
  def med(): Double = Information.toArray.sortWith(_ < _)(Information.length / 2)
}
