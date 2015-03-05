package eu.gruchala.scala.language

import java.util.Date

import org.scalatest._
import org.scalatest.Matchers._
import org.scalatest.{GivenWhenThen, FunSpec}

class Variables extends FunSpec with GivenWhenThen {

  describe("Variables in Scala") {
    it("come in two flavours - immutable values, created with val keyword"){
      val canBeMutated = false
      /// youCantChangeMe = 43 /// this won't compile, you can check yourself
    }

    it("and mutable variables, created with var "){
      var canBeMutated = true
      canBeMutated = false /// now it's fine
      canBeMutated shouldBe false
    }

    it("but we usually prefer the first one, less change in the system means less bugs") {
      val youCantSafelyShareMe = 42
    }

    it("don't need to put a type since it's inferred, but you can add it to improve readability and compile times") {
      def returnString(): String = "Let's return a String to satisfy the type requirement"
      def returnTypesCanBeInferredToo() = "It's inferred as String"

      val implicitType: String = "I'm a String and you can know it"
      implicitType shouldBe a [String]

      val inferredType = "I'm a String"
      inferredType shouldBe a [String]

      val itsInferredFromFunctions = returnString()
      itsInferredFromFunctions shouldBe a [String]

      val itsInferredFromFunctionsWithoutType = returnTypesCanBeInferredToo()
      itsInferredFromFunctionsWithoutType shouldBe a [String]
    }

    it("can deferred until really needed - we call them lazy") {
      lazy val imLazy = new Date
      Thread.sleep(1000)
      val imEager = new Date()
    }
  }
}
