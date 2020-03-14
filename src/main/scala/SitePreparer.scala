import Operation._
import akka.actor.Actor

import scala.util.Random

class SitePreparer extends Actor {
  context.parent ! new Quantity(-Constants.Materials.Concrete)

  def receive: Receive = {
    case Delivered =>
      if (Random.nextInt(99) > 79) throw BadWeatherException()
      context.parent ! SitePrepared
      context.stop(self)
  }
}
