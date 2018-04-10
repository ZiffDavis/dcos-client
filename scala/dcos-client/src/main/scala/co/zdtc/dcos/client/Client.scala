package co.zdtc.dcos.client

import cats.effect.IO
import co.zdtc.dcos.auth.Authenticator
import org.http4s.Uri

class Client(dcosRootUri: Uri)(implicit http: org.http4s.client.Client[IO]) {
  implicit val auth = new Authenticator(dcosRootUri)
  def kafka(serviceName: String) = new KafkaApiClient(dcosRootUri, serviceName)
  def cassandra = new CassandraApiClient(dcosRootUri)
}
