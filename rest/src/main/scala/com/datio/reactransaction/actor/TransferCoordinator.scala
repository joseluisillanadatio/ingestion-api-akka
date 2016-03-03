package com.datio.reactransaction.actor

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import com.datio.reactransaction.model.{Transaction, Transfer}
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

import scala.util.{Failure, Success}


class TransferCoordinator extends Actor with ActorLogging {

  implicit val _timeout = Timeout(5 seconds)
  implicit val ec = ExecutionContext.global

  def props(): Props = Props(new TransferCoordinator())

  def storeMovement(amount: BigDecimal): Unit = {
    // stores the movement in movements db
    log.info(s"guardando movimiento por valor $amount")
  }

  def receive: Actor.Receive = {
    case Transaction(src, dst, amount) => {

      val srcActor = getOrCreateAccountActor(src)
      val dstActor = getOrCreateAccountActor(dst)
      log.info(s"Transaction from account $src to account $dst of $amount euros.")
      val futureSrc = srcActor ? Transfer(-amount)
      val futureDst = dstActor ? Transfer(amount)

      futureSrc onComplete {
        case Success(transaction) =>
          log.info(s"operacion finalizada con exito $transaction")
        case Failure(transaction) =>
          log.info(s"operacion finalizada con fallo, ejecutando fallback $transaction")

      }

      futureDst onComplete {
        case Success(transaction) =>
          log.info(s"operacion finalizada con exito $transaction")
        case Failure(transaction) =>
          log.info(s"operacion finalizada con fallo, ejecutando fallback $transaction")

      }

      val purchase = for {
        usd <- futureSrc
        chf <- futureDst

      } yield storeMovement(amount)
    }
  }

  /**
    * Get an account actor reference. Get a child or create it if not exists.
    *
    * @param id Account unique identification.
    * @return a reference to an account actor.
    */
  def getOrCreateAccountActor(id: Long): ActorRef = {

    def createActor(id: Long): ActorRef = {
      context.actorOf(Props(new AccountActor(id)), id.toString)
    }

    context.child(id.toString).getOrElse(createActor(id))
  }

}
