/*
 * Nubank test - Dec 2017
 *
 * author : Mário de Sá Vera
 */


package com.rewards.web

import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.testkit.RouteTestTimeout
import akka.http.scaladsl.server._
import akka.http.scaladsl.model.StatusCodes

import org.scalatest.{ Matchers, WordSpec }

import akka.testkit.TestDuration
import akka.actor.ActorSystem

import Directives._

import com.rewards.model.Entities._


/** the unit testing for WebServer */
class WebServerTest extends WordSpec with Matchers with ScalatestRouteTest {

  // first we start the web service...
  WebServer.startService("localhost",8080)

  val route = WebServer.route

  "The service" should {

    "return a valid health" in { 

       Get("/health") ~> route ~> check {
        status shouldEqual StatusCodes.OK
       }
    }

    "fail gracefully when an invalid input is passed as parameter" in {

       Post("/calc?filename=blablbal") ~> route ~> check {
         responseAs[String] shouldEqual WebServer.INVALID_INPUT_ERR_MSG
       }
    }
  }
}

