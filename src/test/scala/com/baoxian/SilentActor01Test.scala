package com.baoxian

import akka.testkit.{TestActorRef, TestKit}
import akka.actor.{Props, ActorSystem}
import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

/**
 * Created with IntelliJ IDEA.
 * User: yuanhong@gmail.com
 * Date: 12-12-25
 * Time: 下午4:55
 */
class SilentActor01Test extends TestKit(ActorSystem("testsystem"))
  with WordSpec
  with MustMatchers
  with StopSystemAfterAll {

  "A Silent Actor" must {

    "change state when it receives a message, single threaded" in {
      import SilentActorProtocol._
      val silentActor = TestActorRef[SilentActor]
      silentActor ! SilentMessage("whisper")
      silentActor.underlyingActor.state must (contain("whisper"))
    }

    "change state when it receives a message, multi-threaded" in {
      import SilentActorProtocol._
      val silentActor = system.actorOf(Props[SilentActor], "s3")
      silentActor ! SilentMessage("whisper1")
      silentActor ! SilentMessage("whisper2")
      silentActor ! GetState(testActor)
      expectMsg(Vector("whisper1", "whisper2"))
    }

    "A Sending Actor" must {
      "send a message to an actor when it has finished" in {
        import Kiosk01Protocol._

        val props = Props(new Kiosk01(testActor))   // 将testActor作为下一个接收Actor传递进去，以便测试的Actor可以收到验证消息
        val sendingActor = system.actorOf(props, "kiosk1")
        val tickets = Vector(Ticket(1), Ticket(2), Ticket(3))
        val game = Game("Lakers vs Bulls", tickets)
        sendingActor ! game
        expectMsgPF() {
          case Game(_, tickets) => {
            tickets.size must be(game.tickets.size - 1)
          }
        }
      }
    }

    "filter out particular messages" in {
      import FilteringActorProtocol._
      val props = Props(new FilteringActor(testActor, 5))
      val filter = system.actorOf(props, "filter-1")

      filter ! Event(1)
      filter ! Event(2)
      filter ! Event(1)
      filter ! Event(3)
      filter ! Event(1)
      filter ! Event(4)
      filter ! Event(5)
      filter ! Event(5)
      filter ! Event(6)

      val eventIds = receiveWhile() {
        case Event(id) if id <= 5 => id
      }

      eventIds must be (List(1, 2, 3, 4, 5))
      expectMsg(Event(6))

    }
  }
}
