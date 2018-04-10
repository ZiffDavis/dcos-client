package co.zdtc.dcos.client

import cats.effect.IO
import co.zdtc.dcos.auth.Authenticator
import org.http4s.Uri
import org.http4s.client.dsl.Http4sClientDsl

abstract class ApiClient(dcosUri: Uri)(
    implicit protected val auth: Authenticator,
    protected val http: org.http4s.client.Client[IO])
    extends Http4sClientDsl[IO]
