package controllers

import play.api._
import play.api.mvc._
import models._
import play.api.libs.json._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def players = Action {
    implicit val playerWrites = Player.jsonWriter
    Ok(Json.toJson(Player.all))
  }
  
  def addGame(ids: String) = Action.async {
    val idsPattern = """([0-9]+),([0-9]+),([0-9]+),([0-9]+)""".r 
    ids match {
      case idsPattern(id0, id1, id2, id3) => {
        val idsParsed = List(id0, id1, id2, id3)
        val players = idsParsed.map{id => Player.byId(id.toLong) }.flatten
        if(players.size == 4) {
        	val updated = Team(players(0), players(1)).winsAgainst(Team(players(2), players(3)))
          Player.updateScores(List(updated._1.player1, updated._1.player2, updated._2.player1, updated._2.player2))
        	Future(Redirect("/"))
        }
        else Future(BadRequest(ids))
      }
      case _ => Future(BadRequest(ids))
    }    
  }
}
