package com.datio.reactransaction.rest

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.datio.reactransaction.actor.TransferCoordinator
import com.datio.reactransaction.marshalling.TransactionJsonSupport._
import com.datio.reactransaction.model.Transaction

import scala.concurrent.duration._

case class HttpServiceAPI(system: ActorSystem) {
  implicit val timeout = Timeout(5 seconds)

  val transferActor: ActorRef = system.actorOf(Props(new TransferCoordinator()), "httpInterface")

  val route = {
    path("transfer") {
      createTransfer
    }
  }

  /**
    * /transfer behaviour.
    *
    * @return
    */
  def createTransfer: Route = {
    post {
      entity(as[Transaction]) { transaction =>
        transferActor ! transaction
        complete(s"Transaction: Source - ${transaction.src} - Destination: ${transaction.dst} " +
          s"- Amount: ${transaction.amount}")
      }
    }
  }
}
