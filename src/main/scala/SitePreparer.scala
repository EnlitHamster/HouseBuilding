import Operation._
import akka.actor.Actor

class SitePreparer extends Actor {
  def receive: Receive = {
    case PrepareSite =>
      // TODO: possibility of BadWeatherException and InsufficientMaterialsException
      context.parent ! SitePrepared
      context.stop(self)
  }
}
