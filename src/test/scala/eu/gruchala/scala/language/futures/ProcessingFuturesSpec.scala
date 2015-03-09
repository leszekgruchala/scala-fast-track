package eu.gruchala.scala.language.futures

import org.scalatest._
import org.scalatest.Matchers._

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class ProcessingFuturesSpec extends FunSpec with GivenWhenThen {

  describe("To process a Future") {

    it("one must provide execution context") {
      //import scala.concurrent.ExecutionContext.Implicits.global
    }

    it("so code inside a Future will be processed in a new thread") {
      val id = Thread.currentThread().getId
      Future(Thread.currentThread().getId).map(i => i should not equal id)
    }

    it("but one can also define completed successful Future") {
      Future.successful("I am already completed!").isCompleted shouldBe true
    }

    it("as well failed Future") {
      Future.failed(new IllegalStateException()).isCompleted shouldBe true
    }

    it("single Future is easy to process with one of available functions") {
      val f = Future("...processing...")

      f onSuccess {
        case result => result shouldBe a [String]
      }

      f foreach(r => r shouldBe a [String])

      f onFailure {
        case ex => fail("Not invoked for successful futures")
      }

      f onComplete {
        case Success(result) => result shouldBe a [String]
        case Failure(ex) => fail("Not invoked for successful futures")
      }
    }

    it("whereas multiple Futures with for comprehension") {
      val future_A = Future("A")
      val future_B = Future(2)

      val combined = for {
        a <- future_A
        b <- future_B
        c <- Future(5L)
      } yield (a, b, c)

      combined.foreach{
        result =>
          result._1 shouldBe a [String]
          result._2 shouldBe a [Integer]
          result._3 shouldBe a [java.lang.Long]
      }
    }

    it("where for comprehension is just syntactic sugar") {
      val future_A = Future("A")
      val future_B = Future("B")

      future_A.flatMap(
        (f_A) => future_B.flatMap(
          (f_B) => Future("C").map(
            (f_C) => scala.Tuple3(f_A, f_B, f_C)))
      )
    }

    it("one can also go over sequence of Futures") {
      val seqOfFutures: Seq[Future[String]] = Seq(Future("A"), Future("B"))

      val futureOfResults: Future[Seq[String]] = Future.sequence(seqOfFutures)

      futureOfResults foreach(l => l shouldEqual List("A", "B"))
    }

    it("as well travers in parallel to apply some function") {
      val letters = Seq("A", "B", "C", "D")
      val updatedFutureOfResults: Future[Seq[String]] = Future.traverse(letters)(elem => Future(elem.toLowerCase))

      updatedFutureOfResults foreach(l => l should contain only ("a", "b", "c", "d"))
    }
  }
}