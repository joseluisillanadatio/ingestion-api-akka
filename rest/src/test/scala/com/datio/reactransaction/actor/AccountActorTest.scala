package com.datio.reactransaction.actor

import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestActorRef}
import com.datio.reactransaction.model.Transfer
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{MustMatchers, WordSpecLike}

@RunWith(classOf[JUnitRunner])
class AccountActorTest extends TestKit(ActorSystem("testAkkaSystem"))
  with WordSpecLike with MustMatchers {

  "Account actor" must {

    "Receive a positive transfer " in {
      val id = 0
      val actorRef = TestActorRef(new AccountActor(id))

      val amountBeforeTransfer = actorRef.underlyingActor.currentAmount

      actorRef ! Transfer(1)

      actorRef.underlyingActor.currentAmount must be (amountBeforeTransfer + 1)
    }

    "Receive a negative transfer " in {
      val id = 0
      val actorRef = TestActorRef(new AccountActor(id))

      val amountBeforeTransfer = actorRef.underlyingActor.currentAmount

      actorRef ! Transfer(-1)

      actorRef.underlyingActor.currentAmount must be (amountBeforeTransfer - 1)
    }

  }

}
