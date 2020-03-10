import Operation._
import akka.actor.{ActorSystem, Props}

object Main {
  def main(args: Array[String]): Unit = {
    val system: ActorSystem = ActorSystem(s"System")
    system.actorOf(Props[ConstructionCompany], s"Company") ! BuildHouse
  }
}
