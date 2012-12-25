package com.baoxian

import org.scalatest.{ Suite, BeforeAndAfterAll}
import akka.testkit.TestKit

/**
 * Created with IntelliJ IDEA.
 * User: yuanhong@gmail.com
 * Date: 12-12-25
 * Time: 下午4:58
 */
trait StopSystemAfterAll extends BeforeAndAfterAll {
  this: TestKit with Suite =>
  override protected def afterAll() {
    super.afterAll()
    system.shutdown()
  }
}
