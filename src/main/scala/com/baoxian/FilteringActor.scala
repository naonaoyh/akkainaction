package com.baoxian

import akka.actor.{Actor, ActorRef}

/**
 * Created with IntelliJ IDEA.
 * User: yuanhong@gmail.com
 * Date: 12-12-26
 * Time: 上午9:39
 */
object FilteringActorProtocol {
  case class Event(id: Long)
}

class FilteringActor(nextActor: ActorRef,
                      bufferSize: Int) extends Actor {
  import FilteringActorProtocol._

  var lastMessages = Vector[Event]()
  def receive = {
    case msg: Event =>
      if (!lastMessages.contains(msg)) {
        lastMessages = lastMessages :+ msg
        nextActor !msg
        if (lastMessages.size > bufferSize) {
          // discard the oldest
          lastMessages = lastMessages.tail
        }

      }
  }

}
