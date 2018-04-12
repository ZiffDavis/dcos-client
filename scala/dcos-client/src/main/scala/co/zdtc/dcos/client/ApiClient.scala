package co.zdtc.dcos.client

import cats.effect.IO
import org.http4s.client.dsl.Http4sClientDsl

trait ApiClient extends Http4sClientDsl[IO]
