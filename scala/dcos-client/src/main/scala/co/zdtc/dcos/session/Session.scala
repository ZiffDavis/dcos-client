package co.zdtc.dcos.session

import cats.effect.IO
import org.http4s.Status.Successful
import org.http4s.client.UnexpectedStatus
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.dsl.io._
import org.http4s.{EntityDecoder, Header, Request, Uri}

trait Session extends Http4sClientDsl[IO] {
  implicit val http: org.http4s.client.Client[IO]

  def token: IO[AuthToken]
  def refreshToken: IO[AuthToken]

  def authenticatedGet[A](uri: Uri)(implicit d: EntityDecoder[IO, A]): IO[A] = {
    authenticatedGet_[A](uri)(d)
  }

  protected def authenticatedGet_[A](uri: Uri, retries: Int = 2)(
      implicit d: EntityDecoder[IO, A]): IO[A] = {
    if (retries <= 0)
      return IO.raiseError(
        new Exception("DCOS authentication failed too many times"))

    val request: IO[Request[IO]] = for {
      authToken <- token
      req <- GET(uri, Header("Authentication", authToken.getValue))
    } yield req

    http.fetch(request) {
      case Successful(resp) => resp.as[A]
      case Unauthorized(_) =>
        refreshToken
        authenticatedGet_(uri, retries - 1)(d)
      case failedResponse =>
        IO.raiseError(UnexpectedStatus(failedResponse.status))
    }
  }
}
