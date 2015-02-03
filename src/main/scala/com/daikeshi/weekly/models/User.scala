package com.daikeshi.weekly.models

import scalikejdbc._
import org.joda.time.{DateTime}

case class User(
  username: String, 
  dispalyName: Option[String] = None, 
  email: String, 
  createdAt: DateTime) {

  def save()(implicit session: DBSession = User.autoSession): User = User.save(this)(session)

  def destroy()(implicit session: DBSession = User.autoSession): Unit = User.destroy(this)(session)

}
      

object User extends SQLSyntaxSupport[User] {

  override val tableName = "user"

  override val columns = Seq("username", "dispaly_name", "email", "created_at")

  def apply(u: SyntaxProvider[User])(rs: WrappedResultSet): User = apply(u.resultName)(rs)
  def apply(u: ResultName[User])(rs: WrappedResultSet): User = new User(
    username = rs.get(u.username),
    dispalyName = rs.get(u.dispalyName),
    email = rs.get(u.email),
    createdAt = rs.get(u.createdAt)
  )
      
  val u = User.syntax("u")

  override val autoSession = AutoSession

  def find(username: String)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.eq(u.username, username)
    }.map(User(u.resultName)).single.apply()
  }
          
  def findAll()(implicit session: DBSession = autoSession): List[User] = {
    withSQL(select.from(User as u)).map(User(u.resultName)).list.apply()
  }
          
  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls"count(1)").from(User as u)).map(rs => rs.long(1)).single.apply().get
  }
          
  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.append(sqls"${where}")
    }.map(User(u.resultName)).single.apply()
  }
      
  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[User] = {
    withSQL {
      select.from(User as u).where.append(sqls"${where}")
    }.map(User(u.resultName)).list.apply()
  }
      
  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls"count(1)").from(User as u).where.append(sqls"${where}")
    }.map(_.long(1)).single.apply().get
  }
      
  def create(
    username: String,
    dispalyName: Option[String] = None,
    email: String,
    createdAt: DateTime)(implicit session: DBSession = autoSession): User = {
    withSQL {
      insert.into(User).columns(
        column.username,
        column.dispalyName,
        column.email,
        column.createdAt
      ).values(
        username,
        dispalyName,
        email,
        createdAt
      )
    }.update.apply()

    User(
      username = username,
      dispalyName = dispalyName,
      email = email,
      createdAt = createdAt)
  }

  def save(entity: User)(implicit session: DBSession = autoSession): User = {
    withSQL {
      update(User).set(
        column.username -> entity.username,
        column.dispalyName -> entity.dispalyName,
        column.email -> entity.email,
        column.createdAt -> entity.createdAt
      ).where.eq(column.username, entity.username)
    }.update.apply()
    entity
  }
        
  def destroy(entity: User)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(User).where.eq(column.username, entity.username) }.update.apply()
  }
        
}
