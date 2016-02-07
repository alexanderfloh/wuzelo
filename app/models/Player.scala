package models

import org.joda.time.DateTime
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import language.postfixOps
import play.api.libs.json._


case class Player(
    id: Long, 
    firstName: String, 
    lastName: String,
    elo: Int = Player.DefaultElo,
    lastElo: Int = Player.DefaultElo,
    rd: Int = Player.DefaultRd,
    lastRd: Int = Player.DefaultRd,
    playedGames: Int = 0,
    lastGame: DateTime = Player.DefaultDate
) {
}

object Player {
  val DefaultElo = 1720
  val DefaultRd = 350
  val DefaultDate = new DateTime(2010, 11, 30, 0, 0)
  
  def all = DB.withConnection { implicit c =>
    SQL"select id, firstName, lastName, elo, lastElo, rd, lastRd, playedGames, lastGame from players ORDER BY elo DESC".as(player*)  
  }
  
  def byId(id: Long) = DB.withConnection { implicit c =>
    SQL"select id, firstName, lastName, elo, lastElo, rd, lastRd, playedGames, lastGame from players where id=$id".as(player.singleOpt)  
  }
  
  def add(player: Player) = DB.withConnection { implicit c =>
    //TODO: this should return the updated player object with an `id` set.
    SQL"""insert into players values (
        DEFAULT,
        ${player.firstName},
        ${player.lastName},
        ${player.elo},
        ${player.lastElo},
        ${player.rd},
        ${player.lastRd},
        ${player.playedGames},
        ${player.lastGame.toDate()}
        )""".executeInsert()
  }
  
  def updateScores(players: List[Player]) = DB.withTransaction { implicit t => 
    players.foreach { player => 
      SQL"""update players SET 
        elo=${player.elo}, 
        lastElo=${player.lastElo},
        rd=${player.rd},
        lastRd=${player.lastRd},
        playedGames=${player.playedGames},
        lastGame=${player.lastGame.toDate()}
        WHERE id = ${player.id}""".executeUpdate()  
    }  
  }
  
  def player = {
    get[Long]("id") ~
    get[String]("firstName") ~
    get[String]("lastName") ~
    get[Int]("elo") ~
    get[Int]("lastElo") ~
    get[Int]("rd") ~
    get[Int]("lastRd") ~
    get[Int]("playedGames")~
    get[DateTime]("lastGame") map {
        case id ~ firstName ~ lastName ~ elo ~ lastElo ~ rd ~ lastRd ~ playedGames ~ lastGame =>
          Player(id, firstName, lastName, elo, lastElo, rd, lastRd, playedGames, lastGame)
      }
  }
  
  val jsonWriter = Writes[Player](b => {
    Json.obj(
      "id" -> b.id,
      "firstName" -> b.firstName,
      "lastName" -> b.lastName,
      "elo" -> b.elo,
      "lastElo" -> b.lastElo,
      "rd" -> b.rd,
      "lastRd" -> b.lastRd,
      "playedGames" -> b.playedGames,
      "lastGame" -> b.lastGame)
  })
  
}