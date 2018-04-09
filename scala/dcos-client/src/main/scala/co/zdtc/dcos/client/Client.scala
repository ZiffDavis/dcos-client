package co.zdtc.dcos.client

import cats.effect.IO
import org.http4s.Uri

class Client(dcosRootUrl: String)(implicit http: org.http4s.client.Client[IO]) {
  val dcosUri: IO[Uri] = IO.fromEither(Uri.fromString(dcosRootUrl))
  def kafka(serviceName: String): KafkaApiClient =
    new KafkaApiClient(dcosUri, serviceName)
  def cassandra: CassandraApiClient = new CassandraApiClient(dcosUri)
}
