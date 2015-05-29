package com.wesovi.demo.route

import org.specs2.mutable.{Before, Specification}
import spray.http.StatusCodes._
import spray.testkit.Specs2RouteTest

/**
 * @author Ivan
 */
class AccountCreationRouteSpec extends Specification
with Specs2RouteTest  with Before{

  // makes test execution sequential and prevents conflicts that may occur when the data is
  // changed simultaneously in the database
  args(sequential = true)

  val accountPath:String = "/account"

  implicit def actorRefFactory = system

  val accountRoute = new AccountRoute

  def before=


  "The EndPoint " should {
    "return ok to a Get request to the ping" in {
      Post(accountPath) ~> accountRoute.route ~> check {
        status === Created
        responseAs[String].toLowerCase() === "ok"
      }
    }
  }
}