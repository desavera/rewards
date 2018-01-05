/*
 * Nubank test - Dec 2017
 *
 * author : Mário de Sá Vera
 */


package com.rewards.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn
import scala.io.Source

import scala.concurrent.Future

import akka.http.scaladsl.server._
import akka.http.scaladsl.model.StatusCodes._

import akka.http.scaladsl.server.directives.MethodDirectives

import com.rewards.RewardEngine
import com.rewards.model.Entities._

import java.io.FileInputStream
import java.io.FileNotFoundException

object WebServer {

  implicit val system = ActorSystem("rewards")
  implicit val materializer = ActorMaterializer()

  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val apiPrefix = "rewards/api"
  var binding:Future[ServerBinding] = null
  val route:Route = 

        post {
          pathPrefix(apiPrefix) {

           path("/calc") {
            parameters('filename) { (filename) =>

              try {

                 val invitations : List[Invitation] = readInvitations(filename)
                 val results = RewardEngine.calculateRewards(invitations)

                 complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1> The rewards are : "  + results + " </h1>"))

              } catch {
  
                 case e:FileNotFoundException => complete("Invalid input... file not found in server local filesystem !")
             
              }
            }
           }
          }
        } ~ get {

          pathPrefix(apiPrefix) {

           path("/health") {
              complete (StatusCodes.OK)
           }
          }
        }

  def main(args: Array[String]) {

    startService("localhost",8080)

    StdIn.readLine() // let it run until user presses return
  }

  def startService(host : String,port : Int) = {

    binding = Http().bindAndHandle(route, host, port)
    println(s"Server online...")
  }

  def stopService() = {

    binding
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
    println(s"Server offline...")
  }

  def readInvitations(fileName : String) : List[Invitation] = {

     val buffer = Source.fromFile(fileName)
     val lines = buffer.getLines.asInstanceOf[List[Invitation]]
     
     buffer.close
     return lines
  }
}
