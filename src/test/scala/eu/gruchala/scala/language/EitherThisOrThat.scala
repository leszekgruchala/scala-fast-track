package eu.gruchala.scala.language

import org.scalatest._
import org.scalatest.Matchers._

import scala.util.Either.{LeftProjection, RightProjection}

class EitherThisOrThat extends FunSpec with GivenWhenThen {

  describe("Either[+A, +B]") {

    it("represents a value of one of two possible types (a disjoint union.) " +
      "Instances of Either are either an instance of scala.util.Left or scala.util.Right. " +
      "These can be used to return normal types or exception as Left and valid type/value as Right.") {
      val either: Either[Exception, Int] = try {
        Right("124123".toInt)
      } catch {
        case e: Exception => Left(e)
        //or any other type
        //case e: Exception => Left("the left side")
      }
      either.right shouldBe a [RightProjection[_, _]]
      either.left shouldBe a [LeftProjection[_, _]]
      either.right.get === 124123
    }

    it("allows to explicitly define Left") {
      val badValue = Left(new IllegalArgumentException)

      badValue.isLeft shouldBe true
      badValue.isRight shouldBe false

      badValue.a shouldBe an [IllegalArgumentException]
      badValue.left shouldBe a [LeftProjection[_, _]]//[LeftProjection[IllegalArgumentException, Nothing]]
      badValue.right shouldBe a [RightProjection[_, _]]//[RightProjection[IllegalArgumentException, Nothing]]
    }

    it("allows to explicitly define Right") {
      val goodValue = Right("The right result")

      goodValue.b shouldBe a [String]
      goodValue.left shouldBe a [LeftProjection[_, _]]//[LeftProjection[Nothing, String]]
      goodValue.right shouldBe a [RightProjection[_, _]]//[RightProjection[Nothing, String]]
    }

    it("allows to pattern match on Either") {
      val either: Either[String, Int] = Left("value")
      either match {
        case Left(l) => l shouldBe "value"
        case Right(r) => fail("Right is not used")
      }
    }

    val goodValue = Right(List(10, 3, 6, 1))
    it("one can maintain Left/Right in functional way") {
      goodValue.right map(_.size) shouldBe Right(4)
      goodValue.right.map(a => a.filter(_ <= 5)) shouldBe Right(List(3, 1))
    }

    it("allows to use for comprehensions") {
      (for (r <- goodValue.right) yield r.head) shouldBe Right(10)
    }
  }
}
