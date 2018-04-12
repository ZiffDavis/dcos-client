package co.zdtc.dcos.client

import co.zdtc.dcos.session.Session
import org.http4s.Uri

class CassandraApiClient(dcosUri: Uri)(implicit session: Session)
    extends ApiClient {
  def endpoints: Seq[Endpoint] = { List() }
}
