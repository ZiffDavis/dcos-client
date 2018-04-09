package co.zdtc.dcos.client

import cats.effect.IO
import co.zdtc.dcos.auth.Auth
import io.circe.generic.auto._
import org.http4s.Uri
import org.http4s.circe._
import org.http4s.dsl.io._

case class KafkaEndpointsResponse()

class KafkaApiClient(dcosUri: IO[Uri], serviceName: String)(
    implicit http: org.http4s.client.Client[IO])
    extends ApiClient(dcosUri) {
  def endpoints: IO[Seq[Endpoint]] = {
    for {
      rootUri <- dcosUri
      token <- Auth.token(rootUri)
      uri = rootUri / "service" / serviceName / "v1/endpoints/broker"
      response <- http.expect(GET(uri))(jsonOf[IO, KafkaEndpointsResponse])
    } yield (Seq[Endpoint]())
  }
}
