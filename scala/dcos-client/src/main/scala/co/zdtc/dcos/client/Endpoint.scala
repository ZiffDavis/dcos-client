package co.zdtc.dcos.client

import cats.effect.IO

case class Endpoint(host: String, port: Int)

object Endpoint {
  def fromString(s: String) = s.split(":") match {
    case Array(host, portStr) =>
      for {
        port <- IO(portStr.toInt)
      } yield (new Endpoint(host, port))
    case _ => IO.raiseError(new Exception("Invalid Endpoint string"))
  }
}
