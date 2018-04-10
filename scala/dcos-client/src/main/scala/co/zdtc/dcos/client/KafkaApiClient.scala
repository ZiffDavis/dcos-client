package co.zdtc.dcos.client

import cats.effect.IO
import cats.implicits._
import co.zdtc.dcos.session.Session
import io.circe.generic.auto._
import org.http4s.Uri
import org.http4s.circe._

case class KafkaEndpointsResponse(address: List[String],
                                  dns: List[String],
                                  vip: String)

class KafkaApiClient(dcosUri: Uri, serviceName: String)(
    implicit session: Session)
    extends ApiClient {
  def endpoints: IO[List[Endpoint]] = {
    val uri = dcosUri / "service" / serviceName / "v1" / "endpoints" / "broker"
    for {
      response <- session.authenticatedGet(uri)(
        jsonOf[IO, KafkaEndpointsResponse])
      endpoints <- response.address.map(Endpoint.fromString(_)).sequence
    } yield (endpoints)
  }
}
