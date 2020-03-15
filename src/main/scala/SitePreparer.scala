import Operation._
import akka.actor.Actor

import scala.util.Random

class SitePreparer extends Actor {
  context.parent ! new Order(Material.Concrete)

  def receive: Receive = {
    case d: Delivery =>
      if (d.Check) {
        if (Random.nextInt(99) > 79) throw BadWeatherException()
        context.parent ! SitePrepared
        context.stop(self)
      } else {
        println(s"[${self.path.name}] Not enough materials")
        context.parent ! new Order(d.Material)
      }
  }
}
