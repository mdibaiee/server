package models

import java.sql.Timestamp
import java.util.Date

import play.api.libs.json.{Json, OFormat}

case class Library(
                    id: Long,
                    name: String,
                    full_name: String,
                    isPrivate: Boolean,
                    isFork: Boolean,
                    description: String,
                    language: String
                  )

object Library extends JsonFormatter {
  implicit val userFormat: OFormat[User] = Json.format[User]
  val TYPE_NORMAL = "NORMAL"
  val TYPE_ADMIN = "ADMIN"
  val STATUS_ACTIVE = "ACTIVE"
  val STATUS_DELETED = "DELETED"
}
