package auth

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject
import akka.stream.Materializer
import play.api.libs.ws.WSClient
import play.api.mvc.{Filter, RequestHeader, Result, Results}
import com.typesafe.config.ConfigFactory

class AuthFilter @Inject() (implicit val mat: Materializer, ec: ExecutionContext, ws: WSClient) extends Filter {
  def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    val result: Future[Result] = nextFilter(requestHeader).map{result =>
      result
    }

    if(requestHeader.path == "/") return result
    val clientID = ConfigFactory.load().getString("oauth.client.id")
    val clientSecret = ConfigFactory.load().getString("oauth.client.secret")

    val isValidToken = TokenValidator.validteToken(requestHeader, clientID, clientSecret, ws)
    isValidToken match {
      case Some(true) => result
      case Some(false) => Future.successful(Results.Unauthorized)
    }
  }
}
