package co.zdtc.dcos.auth

import cats.effect.IO
import org.http4s.Uri

object Auth {
  def token(dcosUri: Uri)(
      implicit http: org.http4s.client.Client[IO]): IO[String] = {
    IO.pure("testtoken")
  }
}
