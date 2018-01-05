/*
 * Nubank test - Dec 2017
 *
 * author : Mário de Sá Vera
 */


package com.rewards.web

import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.testkit.RouteTestTimeout
import akka.http.scaladsl.server._
import akka.http.scaladsl.model.{HttpHeader, StatusCodes}
import akka.http.scaladsl.model.headers.RawHeader

import org.scalatest.{ Matchers, FlatSpec , BeforeAndAfterAll }

import akka.testkit.TestDuration
import akka.actor.ActorSystem

import org.scalamock.scalatest.MockFactory

import scala.concurrent.duration._

import com.rewards.model.Entities._



class WebServerTest extends FlatSpec with Matchers with ScalatestRouteTest with BeforeAndAfterAll {

  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(2.second dilated)

  override def beforeAll(configMap: Map[String, Any]) {
    println("Before!")  // start up your web server or whatever
    WebServer.startService("localhost",8080)
  }     

  override def afterAll(configMap: Map[String, Any]) {
    println("After!")  // shut down the web server
    WebServer.stopService
  }    
  //the route to test
  val route = WebServer.route
/*  
  "The reward endpoint" should "fail gracefully when the filename points to an unknown file in the server driver" in {

       Post("/reward/api?filename=blablbal") ~> route ~> check {
         responseAs[String] shouldEqual "Invalid input... file not found in server local filesystem !"
       }
  }
*/
  "The health endpoint" should "return a valid health" in { 

       Get("rewards/api/health") ~> route ~> check {
        status shouldEqual StatusCodes.OK
       }
  }
}
