package com.wesovi.demo.api

import spray.routing._
import akka.actor.{ActorRef, ActorLogging, Props}
import akka.io.IO
import scala.concurrent.ExecutionContext.Implicits.global
import spray.can.Http
import spray.json.DefaultJsonProtocol
import spray.util.LoggingContext
import spray.httpx.SprayJsonSupport
import spray.http.HttpHeaders.{`Access-Control-Allow-Origin`, `Access-Control-Allow-Credentials`, `Access-Control-Allow-Headers`, `Access-Control-Allow-Methods`}
import spray.http.HttpMethods._
import spray.http.{StatusCodes, HttpOrigin, SomeOrigins}
import com.wesovi.demo.util.ConfigHolder
import akka.actor.actorRef2Scala
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directive.pimpApply
import com.wesovi.demo.route.AccountRoute
import com.wesovi.demo.{Core,CoreActors,Services}
 

trait CORSSupport extends Directives {
  private val CORSHeaders = List(
    `Access-Control-Allow-Methods`(GET, POST, PUT, DELETE, OPTIONS),
    `Access-Control-Allow-Headers`("Origin, X-Requested-With, Content-Type, Accept, Accept-Encoding, Accept-Language, Host, Referer, User-Agent"),
    `Access-Control-Allow-Credentials`(true)
  )

  def respondWithCORS(origin: String)(routes: => Route) = {
    val originHeader = `Access-Control-Allow-Origin`(SomeOrigins(Seq(HttpOrigin(origin))))
    respondWithHeaders(originHeader :: CORSHeaders) {
      routes ~ options { complete(StatusCodes.OK) }
    }
  }
}

trait Api extends Directives with RouteConcatenation with CORSSupport with ConfigHolder {
  this: CoreActors with Core =>

  val routes =
    respondWithCORS(config.getString("origin.domain")) { 
      pathPrefix("api") { 
        new AccountRoute().route
      }
    }
  
  val rootService = system.actorOf(ApiService.props(config.getString("hostname"), config.getInt("port"), routes))
}

object ApiService {
  
  def props(hostname: String, port: Int, routes: Route) = Props(classOf[ApiService], hostname, port, routes)
}

class ApiService(hostname: String, port: Int, route: Route) extends HttpServiceActor with ActorLogging {
  IO(Http)(context.system) ! Http.Bind(self, hostname, port)

  def receive: Receive = runRoute(route)
}

object ApiRoute {
  case class Message(message: String)

  object ApiRouteProtocol extends DefaultJsonProtocol {
    implicit val messageFormat = jsonFormat1(Message)
  }

  object ApiMessages {
    val UnknownException = "Unknown exception"
    val UnsupportedService = "Sorry, provided service is not supported."
  }
}

abstract class ApiRoute(implicit log: LoggingContext) extends Directives with SprayJsonSupport {

  import com.wesovi.demo.api.ApiRoute.{ApiMessages, Message}
  import com.wesovi.demo.api.ApiRoute.ApiRouteProtocol._

  
}