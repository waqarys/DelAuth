import io.circe.Encoder
import io.circe.syntax._
import play.api.libs.circe.Circe
import play.api.mvc.{Result, Results}

trait JsonResponse {
  self: Circe =>

  def jsonOk[T](entity: T)(implicit en: Encoder[T]) = asJson(Results.Ok(entity.asJson))
  def jsonCreated[T](entity: T)(implicit en: Encoder[T]) = asJson(Results.Created(entity.asJson))

  def asJson(r: Result) = r.as("application/json")
}
