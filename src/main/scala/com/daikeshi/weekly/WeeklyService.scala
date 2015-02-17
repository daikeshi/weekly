package com.daikeshi.weekly

import akka.actor.Actor
import com.daikeshi.weekly.models.{Task, User}
import org.joda.time.DateTime
import org.json4s.jackson.Serialization._
import scalikejdbc._
import scalikejdbc.config.DBs
import spray.routing._

class WeeklyServiceActor extends Actor with WeeklyService {
  DBs.setupAll()

  def actorRefFactory = context

  def receive = runRoute(weeklyRoute)

  override def postStop() = DBs.closeAll()
}

trait WeeklyService extends HttpService {
  import com.daikeshi.weekly.Json4sProtocol._

  val weeklyRoute =
    path("task") {
      parameters('id.as[Long], 'ts.as[Long], 'content.as[String]) { (id, ts, content) ⇒
        put {
          val task = Task(id, new DateTime(ts), content)
          using(DB(ConnectionPool.borrow())) { db ⇒
            db.localTx { implicit session ⇒
              sql"insert into task values (${task.taskId}, ${task.createdAt}, ${task.content})".update().apply()
            }
          }
          complete(task)
        }
      }
    } ~
    path("task" / LongNumber) { id ⇒
      get {
        val task = Task(1L, DateTime.now, "this is a task")
        complete(write[Task](task))
      } ~
      post {
        entity(as[Task]) { task ⇒ complete(s"Update task $id with new info $task\n") }
      } ~
      put { entity(as[Task]) { task ⇒
        complete(s"Create task $id with info $task\n")
      }}
      delete { complete(s"Delete task $id\n") }
    } ~
    path("user" / LongNumber) { id ⇒
      get { complete(s"User $id details\n") } ~
      post { entity(as[User]) { user ⇒ complete(s"Update user$id with new info $user \n") } }
      put { entity(as[User]) { user ⇒ complete(s"Create user$id with info $user \n") } }
    } ~
    path("hello") { get { complete("hello world") } }
}
