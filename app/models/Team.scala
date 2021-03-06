package models

import org.joda.time.DateTime
import org.joda.time.Period
import org.joda.time.Days

case class Team(player1: Player, player2: Player) {
  def winsAgainst(otherTeam: Team): (Team, Team) = {
    import Math._

    val now = DateTime.now
    
    val rd1a = calcRd(this.player1, now) // 379,913
    val rd1b = calcRd(this.player2, now)
    val rd2a = calcRd(otherTeam.player1, now)
    val rd2b = calcRd(otherTeam.player2, now)

    val p = 3 * pow(log(10) / (PI * 800), 2) // 2,518

    val f1a = 1 / sqrt(1 + p * (pow(rd1b, 2) + pow(rd2a, 2) + pow(rd2b, 2))) // 0.720
    val f2a = 1 / sqrt(1 + p * (pow(rd1b, 2) + pow(rd1a, 2) + pow(rd2b, 2)))
    val f1b = 1 / sqrt(1 + p * (pow(rd1a, 2) + pow(rd2a, 2) + pow(rd2b, 2)))
    val f2b = 1 / sqrt(1 + p * (pow(rd1b, 2) + pow(rd2a, 2) + pow(rd1a, 2)))

    val e1a = 1 / (1 + pow(10, (-((player1.elo + player2.elo) / 2 - (otherTeam.player1.elo + otherTeam.player2.elo) / 2) * f1a / 400))) // 0.5
    val e1b = 1 / (1 + pow(10, (-((player1.elo + player2.elo) / 2 - (otherTeam.player1.elo + otherTeam.player2.elo) / 2) * f1b / 400)))
    val e2a = 1 / (1 + pow(10, (-((otherTeam.player1.elo + otherTeam.player2.elo) / 2 - (player1.elo + player2.elo) / 2) * f2a / 400)))
    val e2b = 1 / (1 + pow(10, (-((otherTeam.player1.elo + otherTeam.player2.elo) / 2 - (player1.elo + player2.elo) / 2) * f2b / 400)))

    val q = log(10) / 800 // 0.002878

    val k1a = q * f1a / (1.0d / pow(rd1a, 2) + pow(q, 2) * pow(f1a, 2) * e1a * (1 - e1a)) //224,514
    val k2a = q * f2a / (1 / pow(rd2a, 2) + pow(q, 2) * pow(f2a, 2) * e2a * (1 - e2a))
    val k1b = q * f1b / (1 / pow(rd1b, 2) + pow(q, 2) * pow(f1b, 2) * e1b * (1 - e1b))
    val k2b = q * f2b / (1 / pow(rd2b, 2) + pow(q, 2) * pow(f2b, 2) * e2b * (1 - e2b))

    val newThis = Team(
      player1.copy(
        elo = player1.elo + (k1a * (1 - e1a)).round.toInt,
        lastElo = player1.elo,
        rd = (1 / sqrt(1 / pow(rd1a, 2) + pow(q, 2) * pow(f1a, 2) * e1a * (1 - e1a))).round.toInt,
        lastRd = player1.rd,
        playedGames = player1.playedGames + 1,
        lastGame = now),
      player2.copy(
        elo = player2.elo + (k1b * (1 - e1b)).round.toInt,
        lastElo = player2.elo,
        rd = (1 / sqrt(1 / pow(rd1b, 2) + pow(q, 2) * pow(f1b, 2) * e1b * (1 - e1b))).round.toInt,
        lastRd = player2.rd,
        playedGames = player2.playedGames + 1,
        lastGame = now))

    val newOther = Team(
      otherTeam.player1.copy(
        elo = otherTeam.player1.elo + (k2a * (-e2a)).round.toInt,
        lastElo = otherTeam.player1.elo,
        rd = (1 / sqrt(1 / pow(rd2a, 2) + pow(q, 2) * pow(f2a, 2) * e2a * (1 - e2a))).round.toInt,
        lastRd = otherTeam.player1.rd,
        playedGames = otherTeam.player1.playedGames + 1,
        lastGame = now),
      otherTeam.player2.copy(
        elo = otherTeam.player2.elo + (k2b * (-e2b)).round.toInt,
        lastElo = otherTeam.player2.elo,
        rd = (1 / sqrt(1 / pow(rd2b, 2) + pow(q, 2) * pow(f2b, 2) * e2b * (1 - e2b))).round.toInt,
        lastRd = otherTeam.player2.rd,
        playedGames = otherTeam.player2.playedGames + 1,
        lastGame = now))

    (newThis, newOther)
  }

  def calcRd(player: Player, now: DateTime) = {
    if (player.playedGames == 0) Player.DefaultRd
    else {
      import Math._

      val timeDiff = Days.daysBetween(player.lastGame, now).getDays
      sqrt(pow(player.rd.toDouble, 2) +
        3000 * log(1 + timeDiff) * (timeDiff + 2) / (timeDiff + 50))
    }
  }
}