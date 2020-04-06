package framework

import scala.collection.mutable
import Predicts._

import scala.reflect.ClassTag

class RQueue[T: ClassTag](var size: Int) {
  checkArg(size >= 0, s"Size (%s) must >= 0", size)
  private val Delegate = mutable.Queue[T]()

  def +(element: T): RQueue[T] = {
    checkNotNull(element, s"Cannot add null element to RQueue")
    if (size == 0) return this
    if (Delegate.length == size) Delegate.dequeue
    Delegate.enqueue(element)
    this
  }

  def ++(elements: Array[T]): RQueue[T] = {
    checkNotNull(elements, s"Cannot add null element to RQueue")
    if (size == 0) return this
    for (e <- elements) Delegate.enqueue(e)
    drop()
    this
  }

  def resize(newSize: Int): Unit = {
    checkArg(newSize >= 0, s"Size (%s) must >= 0", newSize)
    size = newSize
    drop()
  }

  def contains(element: T): Boolean = Delegate.contains(checkNotNull(element, s"RQueue cannot contain null element"))
  def length: Int = Delegate.length
  def toArray: Array[T] = Delegate.toArray
  def drop(): Unit = while (Delegate.length > size) Delegate.dequeue
}