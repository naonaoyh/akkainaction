package com.baoxian

import akka.actor.Actor

/**
 * Created with IntelliJ IDEA.
 * User: yuanhong@gmail.com
 * Date: 12-12-26
 * Time: 上午11:07
 */
class EchoActor extends Actor {
  def receive = {
    case msg =>
      sender ! msg
  }

}
