package models

/**
 * Created by yury on 13/10/14.
 */
case class User(username:String, password:String) {
  def checkPassword(password: String): Boolean = this.password == password

  def getAccess = username match {
    case "bob" => "1"
    case "alice" => "2"
    case "jane" => "3"
    case _ => "0"
  }
}

//User lookup
object User {
  val users = List(
    User("bob", "password"),
    User("alice", "password"),
    User("jane", "password")
  )
  def find(username: String):Option[User] = users.filter(_.username == username).headOption
}