package building.framework.structures

class NumInfo(memory: Int) extends RInfo[Double](memory) {
  def avg(): Double = Information.toArray.sum / Information.length
  def max(): Double = Information.toArray.max
  def min(): Double = Information.toArray.min
  def mid(): Double = Information.toArray(Information.length / 2)
  def med(): Double = Information.toArray.sortWith(_ < _)(Information.length / 2)

  def +(value: Double): NumInfo = {<< (>> + value); this}
  def -(value: Double): NumInfo = {<< (>> - value); this}
  def *(value: Double): NumInfo = {<< (>> * value); this}
  def /(value: Double): NumInfo = {<< (>> / value); this}

  override def resize(newMemory: Int): NumInfo = {Information.resize(newMemory); this}
  override def <<(element: Double): NumInfo = {Information + element; this}
  override def <<<(elements: Array[Double]): NumInfo = {Information ++ elements; this}
}
