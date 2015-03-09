package eu.gruchala.scala.language

import org.scalatest._
import org.scalatest.Matchers._

import scala.annotation.tailrec

class RecursionSpec extends FunSpec with GivenWhenThen {

  describe("Recursion") {
    it("is a powerful concept") {
      ""
    }

    it("is a cornerstone of functional programming") {
      "Functional languages as Haskell, Clojure, Schema and many more use recursion as a primary way of doing looping".contains("recursion") shouldBe true
    }

    it("in scala requires a return a return type") {
      //function return types might be inferred, but in case of recursion it might lead to a infinite loop
      def length(l: List[String]): Int = l match {
        case Nil => 0 // recursion base case, zero elements
        case head :: rest => 1 + length(rest) // method is calling itself, at least one element
      }

      length("Recursion consist of two cases - base case when we should end and recursive step".split(" ").toList) shouldBe 15
    }

    it("might be very fast if you use tail recursion optimization, to ensure that mark your recursive functions with @tailrec") {
      //tail recursion, possible if the call to recursive function is the last step
      def length(l: List[String]): Int = {
        @tailrec //assures tail call optimisation, prevents from StackOverflow errors, each iteration is calculated immediately
        def loop(ll: List[String], counter: Int): Int = ll match {
          case Nil => counter
          case head :: rest => loop(rest, counter + 1)
        }
        loop(l, 0)
      }

      length("Method is tail recursive when last call is recursion or returning value".split(" ").toList) shouldBe 12
    }

    // Exercises: explore on your own
    // Add a correct implementation to the `transform` method, remove 'pending' and make those tests green!
    pending
    it("allows us to express every looping function") {
      def transform(l: List[Int], f: (Int) => Int) = l

      val double = (x: Int) => x*2
      transform(List(1,2,3), double) shouldBe List(2,4,6)

      val increment = (x: Int) => x+1
      transform(List(-1,0,1), increment) shouldBe List(0,1,2)
    }
  }
}
