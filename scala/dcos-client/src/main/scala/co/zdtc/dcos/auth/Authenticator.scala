package co.zdtc.dcos.auth

import cats.effect.IO
import org.http4s.Uri

class Authenticator(dcosUri: Uri) {
  def token(implicit http: org.http4s.client.Client[IO]): IO[String] = {
    IO.pure("testtoken")
  }
}
