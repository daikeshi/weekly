package com.daikeshi.weekly.models

import scalikejdbc._
import org.joda.time.{DateTime}

case class UserTask(
  id: Long, 
  taskId: Long, 
  username: String, 
  createdAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = UserTask.autoSession): UserTask = UserTask.save(this)(session)

  def destroy()(implicit session: DBSession = UserTask.autoSession): Unit = UserTask.destroy(this)(session)

}
      

object UserTask extends SQLSyntaxSupport[UserTask] {

  override val tableName = "user_task"

  override val columns = Seq("id", "task_id", "username", "created_at")

  def apply(ut: SyntaxProvider[UserTask])(rs: WrappedResultSet): UserTask = apply(ut.resultName)(rs)
  def apply(ut: ResultName[UserTask])(rs: WrappedResultSet): UserTask = new UserTask(
    id = rs.get(ut.id),
    taskId = rs.get(ut.taskId),
    username = rs.get(ut.username),
    createdAt = rs.get(ut.createdAt)
  )
      
  val ut = UserTask.syntax("ut")

  override val autoSession = AutoSession

  def find(id: Long)(implicit session: DBSession = autoSession): Option[UserTask] = {
    withSQL {
      select.from(UserTask as ut).where.eq(ut.id, id)
    }.map(UserTask(ut.resultName)).single.apply()
  }
          
  def findAll()(implicit session: DBSession = autoSession): List[UserTask] = {
    withSQL(select.from(UserTask as ut)).map(UserTask(ut.resultName)).list.apply()
  }
          
  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls"count(1)").from(UserTask as ut)).map(rs => rs.long(1)).single.apply().get
  }
          
  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[UserTask] = {
    withSQL {
      select.from(UserTask as ut).where.append(sqls"${where}")
    }.map(UserTask(ut.resultName)).single.apply()
  }
      
  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[UserTask] = {
    withSQL {
      select.from(UserTask as ut).where.append(sqls"${where}")
    }.map(UserTask(ut.resultName)).list.apply()
  }
      
  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls"count(1)").from(UserTask as ut).where.append(sqls"${where}")
    }.map(_.long(1)).single.apply().get
  }
      
  def create(
    id: Long,
    taskId: Long,
    username: String,
    createdAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): UserTask = {
    val generatedKey = withSQL {
      insert.into(UserTask).columns(
        column.id,
        column.taskId,
        column.username,
        column.createdAt
      ).values(
        id,
        taskId,
        username,
        createdAt
      )
    }.updateAndReturnGeneratedKey.apply()

    UserTask(
      id = id,
      taskId = taskId,
      username = username,
      createdAt = createdAt)
  }

  def save(entity: UserTask)(implicit session: DBSession = autoSession): UserTask = {
    withSQL {
      update(UserTask).set(
        column.id -> entity.id,
        column.taskId -> entity.taskId,
        column.username -> entity.username,
        column.createdAt -> entity.createdAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }
        
  def destroy(entity: UserTask)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(UserTask).where.eq(column.id, entity.id) }.update.apply()
  }
        
}
