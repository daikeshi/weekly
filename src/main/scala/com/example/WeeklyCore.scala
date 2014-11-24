package com.example

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

object WeeklyCore extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("weekly-core-actor-system")

  // create and start our service actor
  val service = system.actorOf(Props[WeeklyServiceActor], "demo-service")

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}