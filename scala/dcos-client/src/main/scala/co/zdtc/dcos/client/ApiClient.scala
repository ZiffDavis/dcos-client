package co.zdtc.dcos.client

import cats.effect.IO
import org.http4s.MediaType
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.headers.`Content-Type`

trait ApiClient extends Http4sClientDsl[IO] {
  val headers = List(`Content-Type`(MediaType.`application/json`))
}
