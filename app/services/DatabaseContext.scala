package services

import play.api.libs.concurrent.CustomExecutionContext
import scala.concurrent.ExecutionContext
import akka.actor.ActorSystem

import javax.inject._
import com.google.inject.ImplementedBy

// Make sure to bind the new context class to this trait using one of the custom
// binding techniques listed on the "Scala Dependency Injection" documentation page
@ImplementedBy(classOf[DatabaseExecutionContextImpl])
trait DatabaseExecutionContext extends ExecutionContext

class DatabaseExecutionContextImpl @Inject()(system: ActorSystem)
    extends CustomExecutionContext(system, "akka.actor.database")
    with DatabaseExecutionContext
