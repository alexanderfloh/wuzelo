package models

import org.joda.time.DateTime

case class Game(
  team1: Team,
  team2: Team,
  time: DateTime
  ) {
  
}

object Game {
  
}