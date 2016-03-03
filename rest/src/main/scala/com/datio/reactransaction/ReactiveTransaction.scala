package com.datio.reactransaction

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.datio.reactransaction.rest.HttpServiceAPI
import com.typesafe.config.ConfigFactory

object ReactiveTransaction {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("rest")
    implicit val materializer = ActorMaterializer()

    val config = ConfigFactory.load()
    val logger = Logging(system, getClass)

    logger.info("opening conection ...")
    logger.info("host: " + config.getString("http.interface"))
    logger.info("port: " + config.getInt("http.port").toString)

    Http().bindAndHandle(
      HttpServiceAPI(system).route,
      interface = config.getString("http.interface"),
      port = config.getInt("http.port"))
  }

}
