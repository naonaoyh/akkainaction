package com.baoxian

import akka.actor.{Actor, ActorRef}
import sun.security.krb5.internal.Ticket

/**
 * Created with IntelliJ IDEA.
 * User: yuanhong@gmail.com
 * Date: 12-12-26
 * Time: 上午9:10
 */
object Kiosk01Protocol {
  case class Ticket(seat: Int)
  case class Game(name:String, tickets: Seq[Ticket])
}

class Kiosk01(nextKiosk: ActorRef) extends Actor {
  import Kiosk01Protocol._
  def receive = {
    case game @ Game(_, tickets) =>
      nextKiosk ! game.copy(tickets = tickets.tail)
  }

}
