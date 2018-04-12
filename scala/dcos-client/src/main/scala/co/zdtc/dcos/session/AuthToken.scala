package co.zdtc.dcos.session

case class AuthToken(token: String) {
  def isExpired: Boolean = {
    // TODO determine if dcos token is expired
    false
  }
  def getValue: String = token
  override def toString: String = getValue
}
