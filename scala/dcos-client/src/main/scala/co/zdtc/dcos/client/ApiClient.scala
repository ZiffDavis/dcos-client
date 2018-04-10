package co.zdtc.dcos.client

import cats.effect.IO
import co.zdtc.dcos.session.Session
import org.http4s.client.dsl.Http4sClientDsl

abstract class ApiClient(implicit protected val session: Session)
    extends Http4sClientDsl[IO]
