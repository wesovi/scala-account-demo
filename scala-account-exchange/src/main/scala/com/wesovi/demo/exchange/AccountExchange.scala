package com.wesovi.demo.exchange


case class AccountExchange(
    override val muid:String,
    val operation:AccountExchangeOperation.Value,
    val email:String,
    val password:String) extends BaseExchange
    
object AccountExchangeOperation extends Enumeration{
  type AccountExchangeOperation = Value
  val Creation, Deletion = Value
}