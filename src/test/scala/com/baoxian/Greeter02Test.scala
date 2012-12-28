package com.baoxian

import akka.testkit.TestKit
import akka.actor.{UnhandledMessage, Props, ActorSystem}
import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

/**
 * Created with IntelliJ IDEA.
 * User: yuanhong@gmail.com
 * Date: 12-12-26
 * Time: 上午10:55
 */
class Greeter02Test extends TestKit(ActorSystem("testsystem"))
  with WordSpec
  with MustMatchers
  with StopSystemAfterAll {

  "The Greeter" must {
    "say Helllo World! when a Greeing is sent to it" in {

      val props = Props(new Greeter02(Some(testActor)))
      val greeter = system.actorOf(props, "greeter02-1")
      greeter ! Greeting("World")
      expectMsg("Hello World!")
    }

    "say someting else and see what happens" in {
      val props = Props(new Greeter02(Some(testActor)))
      val greeter = system.actorOf(props, "greeter02-2")
      system.eventStream.subscribe(testActor, classOf[UnhandledMessage])
      greeter ! "World"
      expectMsg(UnhandledMessage("World", system.deadLetters, greeter))
    }
  }

}
