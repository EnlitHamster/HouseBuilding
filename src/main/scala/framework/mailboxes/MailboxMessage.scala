package framework.mailboxes

import framework.mailboxes.AccountableMailbox.Priority

case class MailboxMessage(Priority: Int, Handler: Priority)
