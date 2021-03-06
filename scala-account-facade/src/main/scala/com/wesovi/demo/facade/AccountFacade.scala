package com.wesovi.demo.facade

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration
import scala.util.Failure
import scala.util.Success
import com.wesovi.demo.exchange.AccountExchange
import com.wesovi.demo.exchange.AccountExchange
import com.wesovi.demo.facade.persistence.model.AccountDocument
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props
import akka.util.Timeout
import java.util.concurrent.TimeUnit

object AccountApp extends App  {
  val system = ActorSystem("AccountRemoteSystem")
  val remoteActor = system.actorOf(Props[AccountFacade], name = "AccountFacade")
  remoteActor ! "The RemoteActor is alive"
}


trait AccountFacadeApi {
  def getAccountWithEmail(email:String){
    
  }
  
  def removeAccount(email:String){
    
  }
  
  def updateAccount(email:String):AccountDocument={
    null
  }
  
  def createAccount(email:String):AccountDocument={
    val accountDocumentFuture:Future[AccountDocument] = AccountDocument.create(email)
    accountDocumentFuture.onComplete {
      case Success(response)=>println(response)
      case Failure(response)=>println(response)
    }
    
    Await.result(accountDocumentFuture,Timeout(new FiniteDuration(10000,TimeUnit.MILLISECONDS)).duration)
  }
} 

object AccountFacade {
  
  case class createAccount(accountCreationRequest:AccountExchange)

  def props(property:String)=Props(classOf[AccountFacade],property)
}



class AccountFacade extends Actor with ActorLogging with AccountFacadeApi{
  import AccountFacade._
  import scala.concurrent.ExecutionContext.Implicits.global

  def receive = {
    
    case accountExchange:AccountExchange =>{
      log.info(s"Account operation ${accountExchange.operation} with email ${accountExchange.email} and password ${accountExchange.password}!")
      sender().forward(createAccount(accountExchange.email))  
    }
    case _ =>{
      log.error("Unexpected request.")
    }
  }
  
  
  
  
}