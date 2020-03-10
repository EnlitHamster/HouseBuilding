import Message.Operation
import akka.actor.ActorRef

// Exception classes
// Contains all the exceptions that do not require a complex implementation
// thus requiring only a simple definition

abstract class OperationException(private val sender: ActorRef,
                                       private val onOp: Operation,
                                       private val message: String,
                                       private val cause: Throwable)
  extends Exception(message, cause) {
  val Sender: ActorRef = sender
  val Op: Operation = onOp
}

final case class BadWeatherException(private val sender: ActorRef,
                                     private val onOp: Operation,
                                     private val message: String = "Bad weather",
                                     private val cause: Throwable = None.orNull)
  extends OperationException(sender, onOp, message, cause)

final case class InsufficientMaterialsException(private val message: String = "Insufficient materials",
                                                private val cause: Throwable = None.orNull)
  extends Exception(message, cause)