import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import models._

@RunWith(classOf[JUnitRunner])
class UpdateSpec extends Specification {
  "Application" should {
    "update correctly for new players" in {
      val team1 = Team(Player(1, "Player1", "LastName"), Player(2, "Player2", "LastName"))
      val team2 = Team(Player(3, "Player3", "LastName"), Player(4, "Player4", "LastName"))
      
      val updated = team1.winsAgainst(team2)
      
      updated._1.player1.elo must equalTo(1832)
      updated._1.player1.lastElo must equalTo(1720)
      updated._1.player1.rd must equalTo(329)
      updated._1.player1.lastRd must equalTo(350)
      
      updated._1.player2.elo must equalTo(1832)
      updated._1.player2.lastElo must equalTo(1720)
      updated._1.player2.rd must equalTo(329)
      updated._1.player2.lastRd must equalTo(350)
      
      
      updated._2.player1.elo must equalTo(1608)
      updated._2.player1.lastElo must equalTo(1720)
      updated._2.player1.rd must equalTo(329)
      updated._2.player1.lastRd must equalTo(350)
      
      updated._2.player2.elo must equalTo(1608)
      updated._2.player2.lastElo must equalTo(1720)
      updated._2.player2.rd must equalTo(329)
      updated._2.player2.lastRd must equalTo(350)
    }
  }
}