package com.baoxian

import akka.actor._
import com.baoxian.Greeting

/**
 * Created with IntelliJ IDEA.
 * User: yuanhong@gmail.com
 * Date: 12-12-26
 * Time: 上午10:17
 */
object HelloWorld extends App {
  val system = ActorSystem("helloworld")
  val greeter = system.actorOf(Props[Greeter], "greeter")

  greeter ! Greeting("World")

}

case class Greeting(who: String)

class Greeter extends Actor with ActorLogging {
  def receive = {
    case Greeting(who) => log.info("Hello " + who + "!")
  }
}

class Greeter02(listener: Option[ActorRef] = None)
  extends Actor with ActorLogging {
  def receive = {
    case Greeting(who) =>
      val message = "Hello " + who + "!"
      log.info(message)
      listener.foreach(_ ! message)
  }
}
