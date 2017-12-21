package rewards.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn

import rewards.RewardsEngine
import rewards.model.Entities._

object WebServer {
  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-rewards")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val route:Route = =
        get {
          pathPrefix("filename"/String) { filename =>
             val invitations : List[Invitation] = readInvitations(filename)
             val results = RewardsEngine.calculateRewards(invitations)

             complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1> The rewards are : "  + results + " </h1>"))
        }

    val bindingFuture = Http().bindAndHandle(route, "10.168.68.141", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

  def readInvitations(filename : String) : List[Invitation] {

     val list = "0 1 9 3 0 1"

  }
}
