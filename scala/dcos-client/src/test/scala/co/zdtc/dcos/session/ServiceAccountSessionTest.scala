package co.zdtc.dcos.session

import cats.effect.IO
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.{HttpService, Uri}
import org.http4s.circe._
import org.http4s.dsl.io._
import org.scalatest.{Matchers, WordSpec}
import org.scalamock.scalatest.MockFactory

class ServiceAccountSessionTest
    extends WordSpec
    with Matchers
    with MockFactory {

  val rootUrl =
    IO.fromEither(Uri.fromString("http://localhost:8080")).unsafeRunSync()
  val tokenUrl = Root / "service/kafka/v1/endpoints/broker"

  "ServiceAccountAuthenticator" when {
    "authenticating the first time" should {
      "return a new token" ignore {}
    }
    "already authenticated and unexpired" should {
      "return the same token" ignore {}
    }
    "expired" should {
      "return a new token" ignore {}
    }
    "an error occurs" should {
      "return an error IO" ignore {}
    }
  }
}
