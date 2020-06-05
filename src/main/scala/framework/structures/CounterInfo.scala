package framework.structures

import scala.language.postfixOps

class CounterInfo(memory: Int) extends RInfo[Int](memory) {
  def ++ : CounterInfo = {<< (>> + 1); this}

  override def resize(newMemory: Int): CounterInfo = {Information.resize(newMemory); this}
  override def <<(element: Int): CounterInfo = {Information + element; this}
  override def <<<(elements: Array[Int]): CounterInfo = {Information ++ elements; this}
}
