import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.api.libs.circe.Circe

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with JsonResponse {

  def home: Action[AnyContent] = {
    Action(Ok("Welcome"))
  }
}
