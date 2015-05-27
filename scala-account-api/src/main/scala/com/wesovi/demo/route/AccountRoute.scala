package com.wesovi.demo.route

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.util.Failure
import scala.util.Success
import com.wesovi.demo.ActorLocator
import com.wesovi.demo.api.ApiRoute
import com.wesovi.demo.service.AccountService
import com.wesovi.demo.service.AccountService.CreateAccountMessage
import akka.actor.ActorRef
import akka.actor.ActorRefFactory
import akka.pattern.ask
import akka.pattern.ask
import akka.util.Timeout
import spray.http.MediaTypes
import spray.http.StatusCodes
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing._
import spray.routing.Directive.pimpApply
import spray.util.LoggingContext
import com.wesovi.demo.domain.AccountCreationRequest
import com.wesovi.demo.domain.AccountResponse


 
object AccountRoute {

  
}

class AccountRoute (implicit ec: ExecutionContext, actorRefFactory:ActorRefFactory,log: LoggingContext) extends ApiRoute{
  
  import AccountRoute._
  import com.wesovi.demo.domain._
  
  import com.wesovi.demo.api.ApiRoute._
  import ApiRouteProtocol._
  import ExecutionContext.Implicits.global
  
  
  
  implicit val timeout = Timeout(10 seconds)

  val accountService = ActorLocator.accountService(actorRefFactory)
  
  def createAccount(accountCreationRequest:AccountCreationRequest,ctx:RequestContext)={
    val future:Future[Any] = accountService ? CreateAccountMessage(accountCreationRequest) 
    future.mapTo[AccountResponse] onComplete {
      case Success(data) => {
        ctx.complete(StatusCodes.Created,data)
      }
      case Failure(ex) => println(ex)
    }
  }
  
  
  val route: Route =
  path("account" / "status"){
    get{
      respondWithMediaType(MediaTypes.`application/json`) {
        complete(StatusCodes.Accepted, "ok")
      }
    }
  }~
  path("account"){
    post{
		  respondWithMediaType(MediaTypes.`application/json`) {
        entity(as[AccountCreationRequest]){
          accountCreationRequest => {
            requestContext =>
              createAccount(accountCreationRequest,requestContext)
          }
  		  }
		  }
	  }
  }
} 