package com.datio.reactransaction.poctransaction

import akka.actor.ActorSystem
import akka.testkit.{DefaultTimeout, ImplicitSender, TestKit}
import org.scalatest.{MustMatchers, WordSpecLike}

class ExampleSpec extends TestKit(ActorSystem("testAkkaSystem"))
  with WordSpecLike
  with MustMatchers {
  "My system" must {

    "do this subtask perfect" in {

    }

  }
}

