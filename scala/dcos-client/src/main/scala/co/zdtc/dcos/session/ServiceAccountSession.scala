package co.zdtc.dcos.session

import cats.effect.IO
import org.http4s.Uri

class ServiceAccountSession(
    dcosUri: Uri,
    accountName: String,
    privateKey: String)(implicit val http: org.http4s.client.Client[IO])
    extends Session {
  override def token: IO[String] = {
    IO.pure("testtoken")
  }
}
