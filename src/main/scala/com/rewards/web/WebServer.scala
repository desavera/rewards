/*
 * Nubank test - Dec 2017
 *
 * author : Mário de Sá Vera
 */


package com.rewards.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.MethodDirectives
import akka.http.scaladsl.server._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.StatusCodes._

import akka.stream.ActorMaterializer

import scala.io.StdIn
import scala.io.Source

import scala.concurrent.Future


import com.rewards.RewardEngine
import com.rewards.model.Entities._

import java.io.FileInputStream
import java.io.FileNotFoundException



/** the WebServer for rewards endpoint services */
object WebServer {

  /*
   * Constructors/Initializers
  */
  val INVALID_INPUT_ERR_MSG = "Invalid input... file not found in server local filesystem !"

  implicit val system = ActorSystem("rewards")
  implicit val materializer = ActorMaterializer()

  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  var binding:Future[ServerBinding] = _

  val route:Route = 

      get {
        path("health") {
          complete (StatusCodes.OK)
        }
      } ~ post {
        path("calc") {
            parameters('filename) { (filename) =>

              try {

                 val invitations : List[Invitation] = readInvitations(filename)
                 val results = RewardEngine.calculateRewards(invitations)

                 complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1> The rewards are : "  + results + " </h1>"))

              } catch {
  
                 case e:FileNotFoundException => complete(INVALID_INPUT_ERR_MSG)
             
              }
           }
        }
     }

  /*
   * Methods
  */
  def main(args: Array[String]) {

    startService("localhost",8080)

    StdIn.readLine() // let it run until user presses return

    stopService
  }

  /** Starts the service.
   *
   *  @param host the service host URL
   *  @param port the service port 
   */
  def startService(host : String,port : Int) = {

    binding = Http().bindAndHandle(route, host, port)
    println(s"Server online...")
  }

  /** Stops the service.
   */
  def stopService() = {

    binding
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
    println(s"Server offline...")
  }

  /** Reads the data input from a file in the local filesystem.
   *
   *  @param filename the file path in the local filesystem
   *  @return a list with all invitations
   */
  def readInvitations(fileName : String) : List[Invitation] = {

     val buffer = Source.fromFile(fileName)
     val lines = buffer.getLines.asInstanceOf[List[Invitation]]
     
     buffer.close
     return lines
  }
}
