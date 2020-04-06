import framework.RQueue
import org.scalatest.flatspec.AnyFlatSpec

import scala.language.postfixOps
import scala.reflect.ClassTag

case object RQueueSpec {
  def VoidQueue[T: ClassTag]: RQueue[T] = new RQueue[T](0)
  def EmptyQueue[T: ClassTag](i: Int): RQueue[T] = new RQueue[T](i)
  def TemplateQueue: Int => RQueue[Int] = {i: Int => EmptyQueue[Int](i) ++ List.range(0, i).toArray}
}

class RQueueSpec extends AnyFlatSpec {
  import RQueueSpec._
  s"An RQueue" must s"throw an IAE when creating with negative size" in assertThrows[IllegalArgumentException]{new RQueue[Int](-1)}
  it must s"throw a NPE when adding a null element" in assertThrows[NullPointerException]{VoidQueue[String] + null}
  it must s"throw a NPE when adding a null arrays" in assertThrows[NullPointerException]{VoidQueue[String] ++ null}
  it must s"be empty on creation" in assertResult(0)(new RQueue[Int](10) length)
  it must s"never add elements with size 0" in assertResult(0)(VoidQueue[Int] + 1 length)
  it must s"expand accordingly" in assertResult(1)(EmptyQueue[Int](1) + 1 length)
  it must s"expand accordingly with arrays" in assertResult(5)(EmptyQueue[Int](5) ++ List.range[Int](0, 10).toArray length)
  it must s"add an element at the end" in assertResult(1)(EmptyQueue[Int](1) + 1 toArray 0)
  it must s"add elements at the end" in {
    val Queue: Array[Int] = (EmptyQueue[Int](3) + 0 + 1 + 2).toArray
    for (i <- 0 to 2) assertResult(i)(Queue(i))}
  it must s"add arrays of elements at the end" in {
    val Queue: Array[Int] = TemplateQueue(3).toArray
    for (i <- 0 to 2) assertResult(i)(Queue(i))}
  it must s"confirm when items are contained in the queue" in {
    val Queue: RQueue[Int] = TemplateQueue(5)
    for (i <- 0 to 4) assertResult(true)(Queue.contains(i))
    for (i <- 5 to 10) assertResult(false)(Queue.contains(i))}
}
