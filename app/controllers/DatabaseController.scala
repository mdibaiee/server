package controllers

import javax.inject._

import akka.actor.ActorSystem
import play.api.mvc._
import play.api.db._
import services.DatabaseExecutionContext
import play.api.libs.json._
import play.api.Logger

case class Package(id: Int, name: String)

@Singleton
class DatabaseController @Inject() (db: Database, cc: ControllerComponents, databaseExecutionContext: DatabaseExecutionContext) extends AbstractController(cc) {
  def index = Action {
    implicit val packageJson = Json.format[Package]

    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      val rs   = stmt.executeQuery("SELECT * from packages")

      var pkgs: Array[Package] = Array()
      while (rs.next()) {
        pkgs = pkgs :+ Package(id = rs.getInt("id"), name = rs.getString("name"))
      }

      Ok(Json.toJson(pkgs))
    } catch {
      case e: Exception => {
        Logger.error("error", e)
        NotFound
      }
    } finally {
      conn.close()
    }
  }
}
  
