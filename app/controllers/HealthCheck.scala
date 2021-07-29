package controllers
import javax.inject._
import play.api.mvc.{Action,  AbstractController, ControllerComponents}
import play.api.libs.json._

@Singleton
class HealthCheck @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action {
     Ok("pong")
  }
}

