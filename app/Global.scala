import play.api.mvc.WithFilters
import akka.actor.Props
import play.Play
import play.api.Application
import play.libs.Akka
import play.api.GlobalSettings
import play.Mode
import models.Player

object Global extends  GlobalSettings {
  override def onStart(app: Application) = {
    Play.mode match {
      case Mode.DEV => {
        Player.add(Player(0, "Max", "Mustermann"))
        Player.add(Player(1, "Moritz", "Mustermann"))
        Player.add(Player(2, "Manuel", "Mustermann"))
        Player.add(Player(3, "Maria", "Mustermann"))
      }
      case _ => 
    }
    

  }

  override def onStop(app: Application) {
  }
}