package building.framework.logic

import scala.collection.mutable.ArrayBuffer

// .gitignore
trait PLChoice extends PLElement {
  var choices: ArrayBuffer[PLElement] = ArrayBuffer[PLElement]()
  var choice: Int = _

  def v(elem: PLElement): PLChoice = {
    choices addOne elem
    this
  }

  override def execute(): Unit = choices(choice).execute()
  def select(i: Int): Unit = choice = i
}
