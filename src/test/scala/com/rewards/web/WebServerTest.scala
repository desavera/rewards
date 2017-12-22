package com.rewards.web

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{BeforeAndAfterAll, Inside, Matchers, WordSpec}
import akka.http.scaladsl.server._
import Directives._
import akka.http.scaladsl.model.{HttpHeader, StatusCodes}
import akka.http.scaladsl.model.headers.RawHeader
import org.scalamock.scalatest.MockFactory
import org.scalatest.BeforeAndAfter

import com.rewards.model.Entities._

class WebServerTest extends Matchers with ScalatestRouteTest with Inside with MockFactory
  with BeforeAndAfter {

  //the route to test
  val route = WebServer.route

  "The reward endpoint" should {

    "fail gracefully when the filename points to an unknown file in the server driver" in {

      Post("/reward/api?filename=blablbal") ~> route ~> check {
        responseAs[String] shouldEqual "Invalid request,no such filename in the server local file system..."
      }
    }

  }

  "The health endpoint" should {

    "return a valid health" in {
      Get("/health") ~> route ~> check {
        status shouldEqual StatusCodes.OK
      }
    }
  }

}
