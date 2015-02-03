package com.daikeshi.weekly.models

import scalikejdbc._
import org.joda.time.{DateTime}

case class Task(
  taskId: Long, 
  createdAt: DateTime, 
  content: String) {

  def save()(implicit session: DBSession = Task.autoSession): Task = Task.save(this)(session)

  def destroy()(implicit session: DBSession = Task.autoSession): Unit = Task.destroy(this)(session)

}
      

object Task extends SQLSyntaxSupport[Task] {

  override val tableName = "task"

  override val columns = Seq("task_id", "created_at", "content")

  def apply(t: SyntaxProvider[Task])(rs: WrappedResultSet): Task = apply(t.resultName)(rs)
  def apply(t: ResultName[Task])(rs: WrappedResultSet): Task = new Task(
    taskId = rs.get(t.taskId),
    createdAt = rs.get(t.createdAt),
    content = rs.get(t.content)
  )
      
  val t = Task.syntax("t")

  override val autoSession = AutoSession

  def find(taskId: Long)(implicit session: DBSession = autoSession): Option[Task] = {
    withSQL {
      select.from(Task as t).where.eq(t.taskId, taskId)
    }.map(Task(t.resultName)).single.apply()
  }
          
  def findAll()(implicit session: DBSession = autoSession): List[Task] = {
    withSQL(select.from(Task as t)).map(Task(t.resultName)).list.apply()
  }
          
  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls"count(1)").from(Task as t)).map(rs => rs.long(1)).single.apply().get
  }
          
  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Task] = {
    withSQL {
      select.from(Task as t).where.append(sqls"${where}")
    }.map(Task(t.resultName)).single.apply()
  }
      
  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Task] = {
    withSQL {
      select.from(Task as t).where.append(sqls"${where}")
    }.map(Task(t.resultName)).list.apply()
  }
      
  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls"count(1)").from(Task as t).where.append(sqls"${where}")
    }.map(_.long(1)).single.apply().get
  }
      
  def create(
    createdAt: DateTime,
    content: String)(implicit session: DBSession = autoSession): Task = {
    val generatedKey = withSQL {
      insert.into(Task).columns(
        column.createdAt,
        column.content
      ).values(
        createdAt,
        content
      )
    }.updateAndReturnGeneratedKey.apply()

    Task(
      taskId = generatedKey, 
      createdAt = createdAt,
      content = content)
  }

  def save(entity: Task)(implicit session: DBSession = autoSession): Task = {
    withSQL {
      update(Task).set(
        column.taskId -> entity.taskId,
        column.createdAt -> entity.createdAt,
        column.content -> entity.content
      ).where.eq(column.taskId, entity.taskId)
    }.update.apply()
    entity
  }
        
  def destroy(entity: Task)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(Task).where.eq(column.taskId, entity.taskId) }.update.apply()
  }
        
}
