package building

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import building.structures.Operation._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import akka.pattern.ask
import com.typesafe.config.ConfigFactory

object Main {
  def main(args: Array[String]): Unit = {
    implicit val Timeout: Timeout = 1 minute

    val System: ActorSystem = ActorSystem(s"System", ConfigFactory.load)
    val Future = System.actorOf(Props[ConstructionCompany], s"Company") ? BuildHouse
    if (Await.result(Future, Timeout.duration) == HouseBuilt) println(s"House built")
    else println(s"Oopsie")
  }
}
