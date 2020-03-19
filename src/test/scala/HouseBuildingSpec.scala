import Operation._
import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, ActorRef, ActorSystem, OneForOneStrategy, Props}
import akka.util.Timeout
import akka.pattern.ask
import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.reflect.ClassTag

class HouseBuildingSpec extends AnyFlatSpec {
  implicit val Timeout: Timeout = 1 minute

  s"A SitePreparer" should s"prepare the site" in test[SitePreparer](SitePrepared)
  s"A BrickLayer" should s"build the walls" in test[BrickLayer](WallsBuilt, s"Bad Weather")
  s"A Painter" should s"paint the interiors" in test[Painter](WallsPainted)
  s"A Fitter" should s"fit the windows" in test[Fitter](WindowsFitted)
  s"A FrameManager" should s"prepare the frame" in test[FrameManager](FramePrepared)
  s"An InteriorManager" should s"prepare the interiors" in test[InteriorManager](InteriorPrepared)
  s"An ExteriorManager" should s"prepare the exteriors" in test[ExteriorManager](ExteriorPrepared, s"Bad weather")
  s"A company" should s"build a house" in assert(Await.result(ActorSystem(s"System").actorOf(Props[ConstructionCompany], s"Tester") ? BuildHouse, Timeout.duration).equals(HouseBuilt))

  def test[Test <: Actor: ClassTag](tests: Any*): Assertion = {
    val Result = Await.result(ActorSystem(s"System").actorOf(Props(new ActorTester[Test]), s"Tester") ? s"Start", Timeout.duration)
    assert(tests.contains(Result))
  }
}

class ActorTester[Test <: Actor: ClassTag] extends Actor {
  var client: ActorRef = _

  override def receive: Receive = {
    case o: Order => sender ! new Delivery(true, o.Material)
    case s"Start" =>
      client = sender
      context.actorOf(Props[Test], s"Tested")
    case msg =>
      client ! msg
      context.stop(self)
  }

  override val supervisorStrategy: OneForOneStrategy = OneForOneStrategy() {
    case e: Exception =>
      context.parent ! e.getMessage
      context.stop(self)
      Stop
  }
}