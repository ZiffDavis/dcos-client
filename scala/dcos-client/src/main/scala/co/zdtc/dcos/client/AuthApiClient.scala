package co.zdtc.dcos.client

import cats.effect.IO
import co.zdtc.dcos.session.AuthToken
import io.circe.generic.auto._
import org.http4s.Status.Successful
import org.http4s.Uri
import org.http4s.circe._
import org.http4s.client.UnexpectedStatus
import org.http4s.dsl.io._

final case class PasswordLogin(uid: String, exp: Int = 0, password: String)

object PasswordLogin {
  implicit val passwordLoginDecoder = jsonOf[IO, PasswordLogin]
  implicit val passwordLoginEncoder = jsonEncoderOf[IO, PasswordLogin]
}

final case class LoginResponse(token: String)
object LoginResponse {
  implicit val loginResponseDecoder = jsonOf[IO, LoginResponse]
  implicit val loginResponseEncoder = jsonEncoderOf[IO, LoginResponse]
}

class AuthApiClient(dcosUri: Uri)(implicit http: org.http4s.client.Client[IO])
    extends ApiClient {
  def passwordLogin(accountName: String, password: String): IO[AuthToken] = {
    val uri = dcosUri / "acs" / "api" / "v1" / "auth" / "login"
    val entity = PasswordLogin(accountName, 0, password)
    val request = POST(uri, entity, headers: _*)
    http.fetch(request) {
      case Successful(resp) =>
        resp.as[LoginResponse].map(login => AuthToken(login.token))
      case failedResponse =>
        IO.raiseError(UnexpectedStatus(failedResponse.status))
    }
  }
}
