package building.framework.logic

import scala.language.postfixOps
import scala.collection.mutable

// .gitignore
trait PLExpression {
  val Definition: mutable.Queue[PLElement] = mutable.Queue[PLElement]()

  def ::(elem: PLElement): PLExpression = {
    Definition enqueue elem
    this
  }

  def contaions(elem: PLElement): Boolean = Definition.contains(elem)
}
