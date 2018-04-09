package co.zdtc.dcos.client

import cats.effect.IO
import org.http4s.Uri

class CassandraApiClient(dcosUri: IO[Uri])(
    implicit http: org.http4s.client.Client[IO])
    extends ApiClient(dcosUri) {
  def endpoints: Seq[Endpoint] = { List() }
}
