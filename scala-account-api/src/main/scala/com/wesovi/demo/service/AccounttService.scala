package com.wesovi.demo.service

import com.wesovi.demo.domain.{AccountCreationRequest,AccountResponse}
import akka.actor.Actor
import akka.actor.ActorLogging 
import akka.actor.Props
import akka.actor.actorRef2Scala 
import com.wesovi.demo.ActorLocator
import com.wesovi.demo.exchange.AccountExchange
import com.wesovi.demo.exchange.AccountExchange
import com.wesovi.demo.exchange.AccountExchangeOperation

object AccountService {
  
  case class CreateAccountMessage(accountCreationRequest:AccountCreationRequest)

  def props(property:String)=Props(classOf[AccountService],property)
}

class AccountService extends Actor with ActorLogging {
  import AccountService._

  def receive = {
    case CreateAccountMessage(accountCreationRequest) => {
      val remoteActor = ActorLocator.accountRemoteActor
      log.info(s"Account with email ${accountCreationRequest.email} and password ${accountCreationRequest.password}!")
      val accountExchange:AccountExchange = AccountExchange("",AccountExchangeOperation.Creation,accountCreationRequest.email,null)
      remoteActor ! accountExchange
      val response = AccountResponse("a1","b2")
      sender() ! response
    }
  }
}