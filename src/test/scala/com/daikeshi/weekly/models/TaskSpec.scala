package com.daikeshi.weekly.models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import org.joda.time.{DateTime}


class TaskSpec extends Specification {

  "Task" should {

    val t = Task.syntax("t")

    "find by primary keys" in new AutoRollback {
      val maybeFound = Task.find(1L)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = Task.findBy(sqls.eq(t.taskId, 1L))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = Task.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = Task.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = Task.findAllBy(sqls.eq(t.taskId, 1L))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = Task.countBy(sqls.eq(t.taskId, 1L))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = Task.create(createdAt = DateTime.now, content = "MyString")
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = Task.findAll().head
      // TODO modify something
      val modified = entity
      val updated = Task.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = Task.findAll().head
      Task.destroy(entity)
      val shouldBeNone = Task.find(1L)
      shouldBeNone.isDefined should beFalse
    }
  }

}
        