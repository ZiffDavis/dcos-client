package co.zdtc.dcos.client

import cats.effect.IO
import co.zdtc.dcos.session.Session
import org.http4s.client.UnexpectedStatus
import org.http4s.dsl.io._
import org.http4s.{EntityDecoder, Uri}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

class KafkaApiClientTest extends WordSpec with Matchers with MockFactory {

  val rootUrl =
    IO.fromEither(Uri.fromString("http://localhost:8080")).unsafeRunSync()
  val endpointsUrl = rootUrl / "service" / "kafka" / "v1" / "endpoints" / "broker"

  "KafkaApiClient" when {
    "API call successful" should {
      val endpointsResp = KafkaEndpointsResponse(
        List("1.2.3.4:9000"),
        List("lb:1025"),
        "vip:9092"
      )
      "get broker endpoints" in {
        val session = mock[Session]
        val client = new KafkaApiClient(rootUrl, "kafka")(session)

        (session
          .authenticatedGet(_: Uri)(
            _: EntityDecoder[IO, KafkaEndpointsResponse]))
          .expects(endpointsUrl, *)
          .returning(IO.pure(endpointsResp))

        val endpoints = List(Endpoint("1.2.3.4", 9000))
        client.endpoints.unsafeRunSync() should be(endpoints)
      }
    }
    "API call fails" should {
      "return an error IO" in {
        val session = mock[Session]
        val client = new KafkaApiClient(rootUrl, "kafka")(session)

        (session
          .authenticatedGet(_: Uri)(
            _: EntityDecoder[IO, KafkaEndpointsResponse]))
          .expects(endpointsUrl, *)
          .returning(IO.raiseError(UnexpectedStatus(InternalServerError)))

        client.endpoints.attempt
          .unsafeRunSync() shouldBe a[Left[_, List[Endpoint]]]
      }
    }
  }
}
