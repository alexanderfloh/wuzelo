package models

import org.joda.time.DateTime
import org.joda.time.Period

case class Team(player1: Player, player2: Player) {
  def winsAgainst(otherTeam: Team): (Team, Team) = {
    import Math._

    val now = DateTime.now
    val lastPlayed = this.player1.lastGame.getOrElse(new DateTime(2010, 11, 30, 0, 0))

    val timeDiff = new Period(lastPlayed, now).getDays
    val rd1a = sqrt(pow(this.player1.rd.toDouble, 2) +
      3000 * log(1 + timeDiff) * (timeDiff + 2) / (timeDiff + 50))
    
    val rd1b = 0
    val rd2a = 0
    val rd2b = 0
      
    val p = 3 * pow(log(10) / PI * 800, 2)
    val f1a = 1 / sqrt(1 + p * (pow(rd1b, 2) + pow(rd2a, 2) + pow(rd2b, 2)))

    return (this, otherTeam)
  }
}