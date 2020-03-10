import Operation._
import akka.actor.Actor

class ExteriorManager extends Actor {
  def receive: Receive = {
    case PrepareExterior =>
      // TODO: possibility of BadWeatherException and InsufficientMaterialsException
      context.parent ! ExteriorPrepared
      context.stop(self)
  }
}
