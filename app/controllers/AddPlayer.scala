package controllers

import play.api._
import play.api.mvc._
import models._
import play.api.libs.json._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.data.Form
import play.api.data.Forms._

class AddPlayer extends Controller {
  case class UserData(
      firstName: String, 
      lastName: String, 
      elo: Option[Int], 
      rd: Option[Int], 
      gamesPlayed: Option[Int])
      
  val userConstraints = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "elo" -> optional(number),
      "rd" -> optional(number),
      "gamesPlayed" -> optional(number(min = 0))
    )(UserData.apply)(UserData.unapply)
  )
  
  def addPlayer() = Action(parse.form(userConstraints)) { implicit request =>
    val userData = request.body
    val newPlayer = Player(
        0, 
        userData.firstName, 
        userData.lastName, 
        elo = userData.elo.getOrElse(Player.DefaultElo),
        rd = userData.rd.getOrElse(Player.DefaultElo),
        playedGames = userData.gamesPlayed.getOrElse(0)
    )
    Player.add(newPlayer)
    
    Redirect("/")
  }
}