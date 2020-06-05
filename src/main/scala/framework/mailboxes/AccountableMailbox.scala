package framework

import akka.actor.ActorSystem
import akka.dispatch.{PriorityGenerator, UnboundedPriorityMailbox}
import com.typesafe.config.Config

// TODO: possibility to introduce messages to the mailbox itself to adapt priority levels

class AccountableMailbox(settings: ActorSystem.Settings, config: Config)
  extends UnboundedPriorityMailbox(PriorityGenerator {
    case _: Exception => 0
    case _: Report => 1
    case _ => 2
  })
