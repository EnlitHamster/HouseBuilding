package framework.structures

import scala.language.postfixOps
import scala.reflect.ClassTag

class RInfo[T: ClassTag](memory: Int) {
  protected val Information: RQueue[T] = new RQueue[T](memory)

  def ?=(obj: Any): Boolean = obj match {
    case obj: T => obj.equals(>>)
    case _ => false
  }

  def ?==(obj: Any): Boolean = obj match {
    case obj: RInfo[T] =>
      val Arr1: Array[T] = obj >>>
      val Arr2: Array[T] = >>>
      var check: Boolean = Arr1.length == Arr2.length
      for (i <- Arr1.indices if check) check &= Arr1(i).equals(Arr2(i))
      check
    case _ => false
  }

  def resize(newMemory: Int): RInfo[T] = {Information.resize(newMemory); this}
  def <<(element: T): RInfo[T] = {Information + element; this}
  def <<<(elements: Array[T]): RInfo[T] = {Information ++ elements; this}
  def >> : T = Information actual
  def >>> : Array[T] = Information toArray
  def size: Int = Information.size
  def length: Int = Information.length
}
