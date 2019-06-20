package controllers

import javax.inject._
import play.api.mvc._

import github4s.Github
import github4s.Github._
import github4s.jvm.Implicits._
import scalaj.http.HttpResponse

import io.circe.generic.auto._, io.circe.syntax._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  val accessToken: Option[String] = sys.env.get("GITHUB4S_ACCESS_TOKEN")

  def getRepo() = {
    val getRepo = Github(accessToken).repos.get("47deg", "github4s")

    getRepo.exec[cats.Id, HttpResponse[String]]() match {
      case Left(e) => e.getMessage
      case Right(r) => r.result.asJson.noSpaces
    }
  }

  def getListOrgRepos() = {

    val listOrgRepos = Github(accessToken).repos.listOrgRepos("bleptek")

    listOrgRepos.exec[cats.Id, HttpResponse[String]]() match {
      case Left(e) => e.getMessage
      case Right(r) => r.result.asJson.noSpaces
    }
  }

  def index = Action {

    Ok(getRepo())
  }

}
