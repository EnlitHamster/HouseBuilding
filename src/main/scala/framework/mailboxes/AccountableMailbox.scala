package framework.mailboxes

import akka.actor.ActorSystem
import akka.dispatch.{PriorityGenerator, UnboundedPriorityMailbox}
import com.typesafe.config.Config
import framework.Report

// TODO: possibility to introduce messages to the mailbox itself to adapt priority levels

class AccountableMailbox(settings: ActorSystem.Settings, config: Config)
  extends UnboundedPriorityMailbox(PriorityGenerator {
    case x: MailboxMessage =>

      0
    case _: Exception => 1
    case _: Report => 2
    case _ => 3
  }) {

  import framework.mailboxes.AccountableMailbox.Priority

  private var nPriorities: Int = 0
  private var priorities: Map[Int, Priority] = Map[Int, Priority]()


}

object AccountableMailbox {
  type Priority = PartialFunction[Any, Int]
}