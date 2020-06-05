package framework

import akka.actor.{Actor, Props}
import framework.mailboxes.MailboxMessage

import scala.reflect.ClassTag

private[framework] case class Cycle(ID: Int)

trait AccountableActor extends Actor {
  protected[framework] var nPreReceives: Int = 0
  protected[framework] var nHandles: Int = 0

  protected[framework] var preReceives: Map[Int, Receive] = Map[Int, Receive]()
  protected[framework] var handles: Map[Int, Receive] = Map[Int, Receive]()

  preReceives += (nPreReceives -> discardMailboxMessages)
  nPreReceives += 1

  final def discardMailboxMessages: Receive = {
    case _: MailboxMessage => return;
  }

  final def handle(receive: Receive): Int = {
    handles += (nHandles -> receive)
    nHandles += 1
    nHandles - 1
  }

  final def unhandle(ID: Int): Unit = handles -= ID
  final def swap(ID: Int, newReceive: Receive): Unit = {
    unhandle(ID)
    handles += (ID -> newReceive)
  }

  override final def receive: Receive = new Receive {
    def isDefinedAt(x: Any): Boolean =
      preReceives.values.exists(p => p.isDefinedAt(x)) ||
      handles.values.exists(p => p.isDefinedAt(x))

    def apply(x: Any): Unit = {
      preReceives.values.foreach(preReceive => if (preReceive.isDefinedAt(x)) preReceive.apply(x))
      handles.values.foreach(handle => if (handle.isDefinedAt(x)) handle.apply(x))
    }
  }
}

object AccountableActor {
  def props[AccActorClass <: Actor: ClassTag]: Props =
    Props[AccActorClass].withDispatcher("framework.accountable-mailbox")
}