package com.graphene.deployment

import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._

case class DeploymentEnv(tenantId: String, tenantName: String, environment: String)

/** Temp class */
class DeploymentEnvTable(tag: Tag) extends Table[(String, String, String)](tag, "EnvTenant") {
  def tenantId = column[String]("tenantId", O.PrimaryKey)
  def tenantName = column[String]("tenantName")
  def environment = column[String]("Environment")

  def * = (tenantId, tenantName, environment)
}

class DeploymentEnvironmentDao {
  val dbConfig = Database.forConfig("db.default")

  def hasTenant(tenantId: String, env: String): Future[Seq[String]] = {
    val db = Database.forConfig("db.default")
    try {

      val depEnv = TableQuery[DeploymentEnvTable]
      val result = (for (row <- depEnv if row.tenantId === tenantId) yield (row.tenantId)).result
      val f: Future[Seq[(String)]] = db.run(result)
      f
    } finally db.close

  }
}