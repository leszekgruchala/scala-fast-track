package eu.gruchala.scala.language

import org.scalatest._
import org.scalatest.Matchers._

class MethodsAndFunctionsSpec extends FunSpec with GivenWhenThen {

  describe("Methods") {
    it("can be procedures which return Unit and have side effects") {
      def pr(x: Int): Unit = println(x)
    }

    it("can be pure functions and referentially transparent") {
      val f = (x: Int) => x

      f shouldBe a [Function1[_, _]]
      f shouldBe a [(_) => _]
      f(3) shouldBe 3
    }

    it("and they don't need return type specified") {
      def square(x: Int) = x * x

      square(5) shouldBe 25
    }
  }

  describe("Functions") {
    it("can be passed as other arguments") {
      val f = (x: Int) => x - 2
      def square(x: Int) = x * x
      (1 to 3).map(f).toList shouldBe List(-1, 0, 1)

      def invoke(x: Int => Int) = x(4)

      invoke(square) shouldBe 16
      invoke(f) shouldBe 2
    }

    it("can be anonymous too named function literals") {
      def invoke(x: Int => Int) = x(4)
      invoke(x => x * x - x) shouldBe 12
    }
  }

  describe("Partial functions PartialFunction[-A, +B]") {
    it("are great for safe data types and values checks") {
      val oneToTen = 1 to 10
      val isEven: PartialFunction[Int, Int] = {
        case x if x % 2 == 0 => x
      }

      // the method collect can use isDefinedAt to select which members to collect
      oneToTen collect isEven shouldBe IndexedSeq(2, 4, 6, 8, 10)
    }

    it("and allow chaining") {
      val onlyOdds = Seq(1, 3, 5, 7)
      val isEven: PartialFunction[Int, Int] = {
        case x if x % 2 == 0 => x
      }
      val isOdd: PartialFunction[Int, Int] = {
        case x if x % 2 == 1 => x
      }
      onlyOdds map (isEven orElse isOdd) shouldBe IndexedSeq(1, 3, 5, 7)
    }
  }

  describe("Partially applied functions") {
    it("are functions which not all of their arguments are passed") {
      def calculateTaxes(price: Double, tax: Double) = price + (price * tax)

      val countForPL = calculateTaxes(_: Double, 0.23)
      val countForUK = calculateTaxes(_: Double, 0.20)

      countForPL(3.12) shouldBe 3.8376

      countForUK(23.62) shouldBe 28.344
    }
  }
}