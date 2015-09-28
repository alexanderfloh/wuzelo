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
    lastGame: Option[DateTime] = None
) {
}

object Player {
  private val DefaultElo = 1720
  private val DefaultRd = 350
}