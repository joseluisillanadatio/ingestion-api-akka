package com.datio.reactransaction.actor

import akka.actor.{Actor, ActorLogging}
import com.datio.reactransaction.model.Transfer

import scala.util.Random

/**
  * Actor who represents a bank account.
  *
  * @param id account number identification.
  */
class AccountActor(id: Long) extends Actor with ActorLogging {

  var currentAmount = BigDecimal(new Random().nextInt(AccountActor.HUNDRED))

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    log.info(s"Actor: $id with $currentAmount euros created.")

    // consultar en cassandra esta cuenta
  }

  override def receive: Receive = {
    case Transfer(amount) => {
      currentAmount += amount
      log.info(s"Actor: $id, CurrentAmount: $currentAmount")
      sender () ! Transfer(amount)
    }
  }
}

object AccountActor {
  val HUNDRED = 100
}
