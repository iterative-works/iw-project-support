package example

import zio._
import zio.json._

// Simple example class
case class User(id: String, name: String, email: String)

object User {
  // JSON codecs using ZIO JSON
  implicit val encoder: JsonEncoder[User] = DeriveJsonEncoder.gen[User]
  implicit val decoder: JsonDecoder[User] = DeriveJsonDecoder.gen[User]
}

object Example extends ZIOAppDefault {
  
  val program = for {
    _ <- Console.printLine("Starting example application")
    user = User("1", "John Doe", "john@example.com")
    json <- ZIO.succeed(user.toJson)
    _ <- Console.printLine(s"User as JSON: $json")
    parsed <- ZIO.fromEither(json.fromJson[User])
    _ <- Console.printLine(s"Parsed user: $parsed")
  } yield ()
  
  def run = program
}