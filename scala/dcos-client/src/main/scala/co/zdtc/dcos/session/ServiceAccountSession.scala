package co.zdtc.dcos.session

import cats.effect.IO
import co.zdtc.dcos.client.AuthApiClient
import org.http4s.Uri

class ServiceAccountSession(
    dcosUri: Uri,
    accountName: String,
    privateKey: String)(implicit val http: org.http4s.client.Client[IO])
    extends Session {
  // TODO use a State monad for this instead of var
  var maybeAuthToken: Option[IO[AuthToken]] = None

  override def refreshToken: IO[AuthToken] = {
    val authClient = new AuthApiClient(dcosUri)
    val authToken = authClient.passwordLogin(accountName, privateKey)
    maybeAuthToken = Some(authToken)
    authToken
  }

  override def token: IO[AuthToken] = maybeAuthToken match {
    case Some(io) =>
      io.flatMap { tok =>
        if (tok isExpired) refreshToken else io
      }
    case None => refreshToken
  }
}
