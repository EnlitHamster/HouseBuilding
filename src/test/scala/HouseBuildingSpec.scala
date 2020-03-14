import Operation._

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask

import org.scalatest.flatspec.AnyFlatSpec

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class HouseBuildingSpec extends AnyFlatSpec {
  val System: ActorSystem = ActorSystem(s"System")

  "A company" should "build a house" in {
    implicit val Timeout: Timeout = 1 minute
    val Future = System.actorOf(Props[ConstructionCompany], s"Company") ? BuildHouse
    assert(Await.result(Future, Timeout.duration) === HouseBuilt)
  }
}
