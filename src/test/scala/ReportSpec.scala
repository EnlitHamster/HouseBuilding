import framework.structures.{RInfo, RQueue}
import org.scalatest.flatspec.AnyFlatSpec

import scala.language.postfixOps
import scala.reflect.ClassTag

case object ReportSpec {
  def VoidQueue[T: ClassTag]: RQueue[T] = new RQueue[T](0)
  def EmptyQueue[T: ClassTag](i: Int): RQueue[T] = new RQueue[T](i)
  def TemplateQueue: Int => RQueue[Int] = {i: Int => EmptyQueue[Int](i) ++ List.range(0, i).toArray}
}

class ReportSpec extends AnyFlatSpec {
  import ReportSpec._

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
    for (i <- 0 to 4) assertResult(true)(Queue contains i)
    for (i <- 5 to 10) assertResult(false)(Queue contains i)}
  it must s"have the correct top value" in assertResult(4)(TemplateQueue(5) actual)
  it must s"resize correctly" in {
    val Arr: Array[String] = (new RQueue[String](5) ++ Array[String](s"alpha", s"baker", s"charlie", s"delta", s"echo") resize 3).toArray
    assertResult(s"charlie")(Arr(0))
    assertResult(s"delta")(Arr(1))
    assertResult(s"echo")(Arr(2))}

  s"An RInfo" must s"add information correctly" in assertResult(5)(new RInfo[Int](1) << 5 >>)
  it must s"add an array correctly" in assertResult(1)(new RInfo[Int](3) <<< Array[Int](5, 4, 3, 2, 1) >>)
  it must s"correctly compare with top value" in {
    val Info: RInfo[Int] = new RInfo[Int](1) << 3
    assertResult(true)(Info ?= 3)
    assertResult(false)(Info ?= 2)}
  it must s"correctly reconstruct its history" in {
    val History: Array[String] = (new RInfo[String](3) <<< Array[String](s"alpha", s"baker", s"charlie", s"delta")).>>>
    assertResult("baker")(History(0))
    assertResult("charlie")(History(1))
    assertResult("delta")(History(2))}
  it must s"resize correctly" in {
    val History: Array[String] =(new RInfo[String](5) << s"alpha" << s"baker" << s"charlie" << s"delta" << s"echo" << s"foxtrot" resize 3).>>>
    assertResult("delta")(History(0))
    assertResult("echo")(History(1))
    assertResult("foxtrot")(History(2))}
}
