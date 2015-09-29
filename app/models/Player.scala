package models

import org.joda.time.DateTime

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
  private val DefaultElo = 1720
  private val DefaultRd = 350
  private val DefaultDate = new DateTime(2010, 11, 30, 0, 0)
}