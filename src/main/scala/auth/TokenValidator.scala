package auth

import java.nio.charset.StandardCharsets
import java.util.Base64

import com.typesafe.config.ConfigFactory
import io.circe
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import play.api.libs.ws.{WSClient, WSRequest}
import play.api.mvc.RequestHeader

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

case class WSAuthResponse(sub: String, iss: String, active: Boolean, token_type: String, exp: Long, client_id: String, jti: String)

object TokenValidator {

  def validteToken(request: RequestHeader, clientID: String, clientSecret: String, ws: WSClient) = {
    implicit val wsAuthDecoder: Decoder[WSAuthResponse] = deriveDecoder[WSAuthResponse]
    val oauthEndPoint = ConfigFactory.load.getString("oauth.authentication.endpoint")
    val wsRequest: WSRequest = ws.url(oauthEndPoint)

    val token = request.headers.get("token").getOrElse("")
    val encodedClientSecret = "Basic " + Base64.getUrlEncoder.encodeToString(s"$clientID:$clientSecret".getBytes(StandardCharsets.US_ASCII))

    implicit val wsAuthRead = play.api.libs.json.Json.reads[WSAuthResponse]
    val wsResponse: Future[Either[circe.Error, WSAuthResponse]] = wsRequest
      .withMethod("POST")
      .withHttpHeaders("Authorization" -> encodedClientSecret, "Content-Type" -> "application/x-www-form-urlencoded")
      .post(Map("token" -> Seq(token)))
      .map{wsResponse =>
        val responseBodyStr = wsResponse.underlying[play.shaded.ahc.org.asynchttpclient.Response].getResponseBody.mkString
        val wsAuthValue = io.circe.parser.parse(responseBodyStr).flatMap(a => a.as[WSAuthResponse](wsAuthDecoder))
        wsAuthValue
      }

    val decodedWSResponse: Either[circe.Error, WSAuthResponse] = Await.result(wsResponse, Duration.Inf)
    decodedWSResponse match {
      case Right(wsAuth) => Some(wsAuth.active)
      case Left(_) => Some(false)
    }
  }
}
