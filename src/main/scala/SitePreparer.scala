import Message.Operation._
import akka.actor.Actor

import scala.util.Random

class SitePreparer extends Actor {
  def receive: Receive = {
    case PrepareSite =>
      // TODO: possibility of InsufficientMaterialsException - requires materials
      if (Random.nextInt(99) > 79) throw BadWeatherException(self, PrepareSite)
      context.parent ! SitePrepared
      context.stop(self)
  }
}
