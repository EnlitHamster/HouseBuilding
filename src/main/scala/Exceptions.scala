import akka.actor.ActorRef

// Exception classes
// Contains all the exceptions that do not require a complex implementation
// thus requiring only a simple definition

final case class BadWeatherException(private val message: String = "Bad weather",
                                     private val cause: Throwable = None.orNull)
  extends Exception(message, cause)

final case class InsufficientMaterialsException(private val message: String = "Insufficient materials",
                                                private val cause: Throwable = None.orNull)
  extends Exception(message, cause)