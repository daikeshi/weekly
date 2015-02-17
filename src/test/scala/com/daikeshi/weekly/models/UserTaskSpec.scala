package com.daikeshi.weekly.models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import org.joda.time.{DateTime}


class UserTaskSpec extends Specification {

  "UserTask" should {

    val ut = UserTask.syntax("ut")

    "find by primary keys" in new AutoRollback {
      val maybeFound = UserTask.find(1L)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = UserTask.findBy(sqls.eq(ut.id, 1L))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = UserTask.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = UserTask.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = UserTask.findAllBy(sqls.eq(ut.id, 1L))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = UserTask.countBy(sqls.eq(ut.id, 1L))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = UserTask.create(1L, 1L, "MyString")
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = UserTask.findAll().head
      // TODO modify something
      val modified = entity
      val updated = UserTask.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = UserTask.findAll().head
      UserTask.destroy(entity)
      val shouldBeNone = UserTask.find(1L)
      shouldBeNone.isDefined should beFalse
    }
  }

}
