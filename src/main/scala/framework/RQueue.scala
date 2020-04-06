package framework

import scala.collection.mutable
import Predicts._

import scala.reflect.ClassTag

class RQueue[T: ClassTag](val Size: Int) {
  checkArg(Size >= 0, s"Size (%s) must >= 0", Size)
  private val Delegate = mutable.Queue[T]()

  def +(element: T): RQueue[T] = {
    checkNotNull(element, s"Cannot add null element to RQueue")
    if (Size == 0) return this
    if (Delegate.length == Size) Delegate.dequeue
    Delegate.enqueue(element)
    this
  }

  def ++(elements: Array[T]): RQueue[T] = {
    checkNotNull(elements, s"Cannot add null element to RQueue")
    if (Size == 0) return this
    val Delta: Int = Size - Delegate.length
    val CollSize: Int = elements.length
    val Iterator: Iterator[T] = elements.iterator
    for (_ <- 0 until Math.min(Delta, CollSize)) Delegate.enqueue(Iterator.next)
    for (_ <- 0 until Math.max(0, CollSize - Delta)) Delegate.enqueue(Iterator.next)
    this
  }

  def contains(element: T): Boolean = Delegate.contains(checkNotNull(element, s"RQueue cannot contain null element"))
  def length: Int = Delegate.length
  def toArray: Array[T] = Delegate.toArray
}