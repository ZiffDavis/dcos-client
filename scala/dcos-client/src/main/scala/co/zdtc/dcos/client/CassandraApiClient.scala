package co.zdtc.dcos.client

import cats.effect.IO
import co.zdtc.dcos.auth.Authenticator
import org.http4s.Uri

class CassandraApiClient(dcosUri: Uri)(implicit auth: Authenticator,
                                       http: org.http4s.client.Client[IO])
    extends ApiClient(dcosUri) {
  def endpoints: Seq[Endpoint] = { List() }
}
