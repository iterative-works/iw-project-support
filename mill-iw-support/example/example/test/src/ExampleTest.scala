package example

import zio._
import zio.json._
import zio.test._
import zio.test.Assertion._

object ExampleTest extends ZIOSpecDefault {
  override def spec = suite("Example suite")(
    test("User JSON serialization") {
      val user = User("1", "John Doe", "john@example.com")
      val json = user.toJson
      val expected =
        """{"id":"1","name":"John Doe","email":"john@example.com"}"""

      assertTrue(json == expected)
    },
    test("User JSON deserialization") {
      val json = """{"id":"1","name":"John Doe","email":"john@example.com"}"""
      val parsed = json.fromJson[User]
      val expected = User("1", "John Doe", "john@example.com")

      assertTrue(parsed == Right(expected))
    }
  )
}
