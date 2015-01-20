package com.daikeshi

import akka.actor.Actor
import com.daikeshi.Models.Task
import spray.routing._
import spray.http._
import MediaTypes._

class WeeklyServiceActor extends Actor with WeeklyService {

  def actorRefFactory = context

  def receive = runRoute(weeklyRoute)
}

trait WeeklyService extends HttpService {
  val weeklyRoute =
    get {
      path("") { complete(s"Hello Weekly") } ~
      path("task" / LongNumber) { id ⇒ complete(s"Task $id details\n") }
      path("user" / LongNumber) { id ⇒ complete(s"User $id details\n") }
    } ~
    post {
      path("task" / LongNumber) { id ⇒ complete(s"Update task $id\n") } ~
      path("user" / LongNumber) { id ⇒ complete(s"Update user$id info\n") }
    } ~
    delete {
      path("task" / LongNumber) { id ⇒ complete(s"Delete task $id\n") }
    }
}
