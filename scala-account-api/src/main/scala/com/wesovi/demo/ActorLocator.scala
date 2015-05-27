package com.wesovi.demo

import akka.actor.ActorRefFactory
import akka.actor.ActorSystem
import akka.actor.Props
import com.wesovi.demo.service.AccountService

object ActorLocator {

  def accountService(implicit ctx: ActorRefFactory) = ctx.actorOf(Props[AccountService])
  
  implicit val system = ActorSystem("AccountSystem-1")
  def accountRemoteActor(implicit ctx: ActorRefFactory) = system.actorSelection("akka.tcp://AccountRemoteSystem@127.0.0.1:20001/account/AccountFacade")
  
}