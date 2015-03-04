package eu.gruchala.scala.language

import org.scalatest._
import org.scalatest.Matchers._

import scala.util.{Success, Failure, Try}

class Trying extends FunSpec with GivenWhenThen {

  describe("Try[T]") {

    it("is a container that holds one of two values: expected return of type T") {
      Try{ 42 }.isSuccess shouldBe true
    }

    it("or an exception") {
      Try{ throw new Exception("This exception could cause trouble, but Try keeps it under control") }.isFailure shouldBe true
    }

    it("that prevents exceptions from unwinding the stack") {
      Try{ throw new Exception("This exception won't crash the program!") }
    }

    val failedCast = Try{ "Casting letters to int will fail with an exception :(".toInt }
    val validCast = Try{ "42".toInt }

    it("but allows us to throw it if we wish to do so") {
      intercept[NumberFormatException] {
        failedCast.get
      }
    }

    it("getting the value for success is also easy") {
      validCast.get shouldBe 42
    }

    it("when you aren't sure what is the outcome Try gives you a few handy tools ") {
      failedCast.getOrElse(123) shouldBe 123
      validCast.map(_ * 2).getOrElse(0) shouldBe 84
    }

    it("works great with patter matching") {
      failedCast match {
        case Failure(exceptionThrownAtRuntime) => exceptionThrownAtRuntime shouldBe a [NumberFormatException]
        case _ => fail("This should not happen")
      }
      validCast match {
        case Success(computedValue) => computedValue shouldBe 42
        case _ => fail("This should not happen")
      }
    }

    it("and with for-comprehension, too") {
      for { v <- validCast } yield { v shouldBe 43 }
      for { v <- failedCast } yield { fail("We do iterate only over Success values") }
    }

    it("allows us to selectively recover from errors with custom actions or default values") {
      val fixedCast = failedCast.recover {
        case _:NumberFormatException => 0
      }
      fixedCast.isSuccess shouldBe true
      fixedCast.get shouldBe 0
    }

    // Exercises: explore on your own
    // Add a correct implementation to the fixThis method, remove 'pending' and make those tests green!

    pending
    it("can be used as Option") {
      def fixThis(arg: Try[Int]): Option[Int] = ???

      fixThis(validCast) shouldBe a [Some[_]]
      fixThis(validCast).isDefined shouldBe true
      fixThis(failedCast).isDefined shouldBe false
    }
  }
}
