package com.datio.reactransaction.actor

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{TestProbe, TestActorRef, TestKit}
import com.datio.reactransaction.model.{Transaction, Transfer}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{MustMatchers, WordSpecLike}

@RunWith(classOf[JUnitRunner])
class TransferCoordinatorTest extends TestKit(ActorSystem("testAkkaSystem"))
  with WordSpecLike with MustMatchers {

  val probeSrc: TestProbe = TestProbe()
  val probeDst = TestProbe()


  val transferActor = TestActorRef(new TransferCoordinator {
    override def getOrCreateAccountActor(id: Long): ActorRef = {
      if (id == 1) probeSrc.ref else probeDst.ref
    }
  })

  "Transfer Coordinator actor" must {

    "make a transfer" in {

      val ten = 10
      val amount = BigDecimal(ten)
      transferActor ! Transaction(1, 2, amount)

      probeSrc.expectMsg(Transfer(-amount))
      probeDst.expectMsg(Transfer(amount))
    }
  }

}
