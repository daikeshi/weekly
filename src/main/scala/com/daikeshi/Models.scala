package com.daikeshi

import spray.http.DateTime

object Models {
  object Task {
    def contentSummary(content: String) = content.split("\\s+").take(10).mkString(" ")
    def apply(id: Long, createdAt: DateTime, content: String, creator: User): Task = {
      Task(id, createdAt, None, contentSummary(content), content, creator, List(creator))
    }
  }
  case class Task(id: Long, createdAt: DateTime, due: Option[DateTime], summary: String, content: String, creator: User, participants: List[User])

  case class User(id: Long, userName: String, createdAt: DateTime, displayName: Option[String])
}
