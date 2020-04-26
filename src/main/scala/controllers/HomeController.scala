package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe {

  def home: Action[AnyContent] = {
    Action(Ok("Welcome"))
  }
}
