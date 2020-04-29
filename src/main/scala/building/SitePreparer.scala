package building

import akka.actor.Actor
import building.structures.Operation._
import building.structures.{Delivery, Material, Order}

import scala.util.Random

class SitePreparer extends Actor {
  context.parent ! Order(Material.Concrete)

  def receive: Receive = {
    case d: Delivery =>
      if (d.Check) {
        if (Random.nextInt(99) > 79) throw BadWeatherException()
        context.parent ! SitePrepared
        context.stop(self)
      } else {
        println(s"[${self.path.name}] Not enough materials")
        context.parent ! Order(d.Material)
      }
  }
}
