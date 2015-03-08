package eu.gruchala.scala.language.futures

import org.scalatest._
import org.scalatest.Matchers._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class ExceptionsOverFutures extends FunSpec with GivenWhenThen {

  describe("To handle exceptions with Futures") {

    it("one must provide execution context") {
      //import scala.concurrent.ExecutionContext.Implicits.global
    }
    it("and posses a failed Future") {
      Future.failed(new IllegalStateException("I worked not so well")).isCompleted shouldBe true
    }

    val failedFuture = Future.failed(new IllegalStateException("I worked not so well"))
    it("failed Future can be handle with onComplete") {
      failedFuture onComplete {
        case Success(result) => fail("Success is not invoked in failed future")
        case Failure(ex) => ex shouldBe a [IllegalStateException]
      }
    }

    it("and with onFailure") {
      failedFuture onFailure {
        case exception => exception shouldBe a [IllegalStateException]
      }
    }

    it("whereas onSuccess will not be invoked at all") {
      failedFuture onSuccess {
        case result => fail("Success is not invoked in failed future")
      }
    }

    it("and same applies to foreach") {
      failedFuture.foreach(_ => fail("Success is not invoked in failed future"))
    }
  }
}
