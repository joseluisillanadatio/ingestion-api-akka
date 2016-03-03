package com.datio.reactransaction.model

/**
  * Represents a transaction between two accounts.
  *
  * @param src    Source account.
  * @param dst    Destination account.
  * @param amount amount of the transaction.
  */
case class Transaction(src: Long, dst: Long, amount: BigDecimal)
