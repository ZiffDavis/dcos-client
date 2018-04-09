package co.zdtc.dcos.client

import cats.effect.IO
import org.http4s.Uri
import org.http4s.client.dsl.Http4sClientDsl

abstract class ApiClient(dcosUri: IO[Uri])(
    implicit protected val http: org.http4s.client.Client[IO])
    extends Http4sClientDsl[IO]
