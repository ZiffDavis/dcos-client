package co.zdtc.dcos.client

import cats.effect.IO
import co.zdtc.dcos.session.AuthToken
import org.http4s.dsl.io._
import org.http4s.{HttpService, Uri}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

class AuthApiClientTest extends WordSpec with Matchers with MockFactory {

  val rootUrl =
    IO.fromEither(Uri.fromString("http://localhost:8080")).unsafeRunSync()
  val testToken = "testtoken"
  val testName = "service"
  val testPassword = "pass"

  val service = HttpService[IO] {
    case req @ POST -> Root / "auth" / "login" =>
      for {
        login <- req.as[PasswordLogin]
        resp <- login match {
          case PasswordLogin(`testName`, 0, Some(`testPassword`), _) =>
            Ok(LoginResponse(testToken))
          case _ => NotFound()
        }
      } yield resp
  }
  implicit val http = org.http4s.client.Client.fromHttpService(service)
  val authClient = new AuthApiClient(rootUrl)

  "AuthApiClient" when {
    "API call successful" should {
      "return an auth token" in {
        val token =
          authClient.passwordLogin(testName, testPassword).unsafeRunSync()
        token should be(AuthToken(testToken))
      }
    }
    "API call fails" should {
      "return an error IO" in {
        val resp = authClient.passwordLogin(testName, "wrong password")
        resp.attempt.unsafeRunSync() shouldBe a[Left[_, AuthToken]]
      }
    }
  }
}
