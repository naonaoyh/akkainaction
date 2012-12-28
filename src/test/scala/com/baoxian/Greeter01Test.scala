package com.baoxian

import akka.testkit.{EventFilter, CallingThreadDispatcher, TestKit}
import com.typesafe.config.ConfigFactory
import akka.actor.{Props, ActorSystem}
import Greeter01Test.testSytem
import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

/**
 * Created with IntelliJ IDEA.
 * User: yuanhong@gmail.com
 * Date: 12-12-26
 * Time: 上午10:23
 */

object Greeter01Test {

  val testSytem = {
    val config = ConfigFactory.parseString(
      """akka.event-handlers = ["akka.testkit.TestEventListener"]""")
    ActorSystem("testsystem", config)

  }
}

class Greeter01Test extends TestKit(testSytem)
  with WordSpec
  with MustMatchers
  with StopSystemAfterAll {

  "The Greeter" must {
    "say Hello World! when a Greeing is sent to it"  in {
      val dispatcherId = CallingThreadDispatcher.Id
      val props = Props[Greeter].withDispatcher(dispatcherId)
      val greeter = system.actorOf(props)
      EventFilter.info(message = "Hello World!",
        occurrences = 1).intercept {
          greeter ! Greeting("World")
      }
    }
  }

}

