package com.datio.reactransaction.marshalling

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.datio.reactransaction.model.Transaction
import spray.json.DefaultJsonProtocol


object TransactionJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val txSupport = jsonFormat3(Transaction)
}
