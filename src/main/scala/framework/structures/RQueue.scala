package framework.structures

import framework.Predicts.{checkArg, checkNotNull}

import scala.collection.mutable
import scala.language.postfixOps
import scala.reflect.ClassTag

class RQueue[T: ClassTag](var size: Int) {
  checkArg(size >= 0, s"Size (%s) must >= 0", size)
  private val Delegate = mutable.Queue[T]()

  private def drop: RQueue[T] = {while (Delegate.length > size) Delegate.dequeue; this}

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
    drop
    this
  }

  def resize(newSize: Int): RQueue[T] = {
    checkArg(newSize >= 0, s"Size (%s) must >= 0", newSize)
    size = newSize
    drop
  }

  def contains(element: T): Boolean = Delegate.contains(checkNotNull(element, s"RQueue cannot contain null element"))
  def length: Int = Delegate.length
  def toArray: Array[T] = Delegate.toArray
  def actual: T = toArray(Delegate.length - 1)
}