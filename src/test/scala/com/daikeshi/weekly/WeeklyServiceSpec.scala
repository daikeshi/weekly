package com.daikeshi.weekly

import org.specs2.mutable.Specification
import spray.http.StatusCodes._
import spray.testkit.Specs2RouteTest

class WeeklyServiceSpec extends Specification with Specs2RouteTest with WeeklyService {
  def actorRefFactory = system
  
  "MyService" should {

    "return a greeting for GET requests to the root path" in {
      Get() ~> weeklyRoute ~> check {
        responseAs[String] must contain("Hello Weekly")
      }
    }

    "leave GET requests to other paths unhandled" in {
      Get("/noroute") ~> weeklyRoute ~> check {
        handled must beFalse
      }
    }

    "return a MethodNotAllowed error for PUT requests to the root path" in {
      Put() ~> sealRoute(weeklyRoute) ~> check {
        status === MethodNotAllowed
        responseAs[String] === "HTTP method not allowed, supported methods: GET, POST, DELETE"
      }
    }
  }
}
