package co.zdtc.dcos.client

import cats.effect.IO
import co.zdtc.dcos.auth.Authenticator
import io.circe.generic.auto._
import org.http4s.{Header, Uri}
import org.http4s.circe._
import org.http4s.dsl.io._
import cats.implicits._

case class KafkaEndpointsResponse(address: Seq[String],
                                  dns: Seq[String],
                                  vip: String)

class KafkaApiClient(dcosUri: Uri, serviceName: String)(
    implicit auth: Authenticator,
    http: org.http4s.client.Client[IO])
    extends ApiClient(dcosUri) {
  def endpoints: IO[List[Endpoint]] = {
    val uri = dcosUri / "service" / serviceName / "v1/endpoints/broker"
    for {
      token <- auth.token
      req = GET(uri, Header("Authentication", token))
      response <- http.expect(uri)(jsonOf[IO, KafkaEndpointsResponse])
      endpoints <- response.address.map(Endpoint.fromString(_)).toList.sequence
    } yield (endpoints)
  }
}
