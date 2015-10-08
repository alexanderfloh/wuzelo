package controllers

import play.api._
import play.api.mvc._
import models.Player
import play.api.libs.json._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def players = Action {
    implicit val playerWrites = Player.jsonWriter
    Ok(Json.toJson(Player.all))
  }
}
