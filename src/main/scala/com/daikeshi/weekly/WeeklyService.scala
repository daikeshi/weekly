package com.daikeshi.weekly

import akka.actor.Actor
import com.daikeshi.weekly.Models.{User, Task}
import org.json4s.jackson.Serialization._
import spray.http.DateTime
import spray.routing._

class WeeklyServiceActor extends Actor with WeeklyService {

  def actorRefFactory = context

  def receive = runRoute(weeklyRoute)
}

trait WeeklyService extends HttpService {
  import com.daikeshi..weekly.Json4sProtocol._

  val weeklyRoute =
    path("task" / LongNumber) { id ⇒
      get {
        val task = Task(1L, DateTime.now, "this is a task", User(1L, "kdai", DateTime.now, Some("Keshi Dai")))
        complete(write[Task](task))
      } ~
      post {
        entity(as[Task]) { task ⇒ complete(s"Update task $id with new info $task\n") }
      } ~
      put { entity(as[Task]) { task ⇒ complete(s"Create task $id with info $task\n") } }
      delete { complete(s"Delete task $id\n") }
    } ~
    path("user" / LongNumber) { id ⇒
      get { complete(s"User $id details\n") } ~
      post { entity(as[User]) { user ⇒ complete(s"Update user$id with new info $user \n") } }
      put { entity(as[User]) { user ⇒ complete(s"Create user$id with info $user \n") } }
    }
}
