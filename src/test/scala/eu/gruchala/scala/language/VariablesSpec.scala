package eu.gruchala.scala.language

import java.util.Date

import org.scalatest._
import org.scalatest.Matchers._

class VariablesSpec extends FunSpec with GivenWhenThen {

  describe("Variables in Scala") {
    it("come in two flavours - immutable values, created with val keyword"){
      val canBeMutated = false
      //canBeMutated = true /// this won't compile, you can check yourself
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
      def returnString(): String = "Let's return a String to satisfy the lazy val imLazy = new Date()ype requirement"
      def returnTypesCanBeInferredToo() = "It's inferred as String"

      val explicitType: String = "I'm a String and you can know it"
      explicitType shouldBe a [String]

      val inferredType = "I'm a String"
      inferredType shouldBe a [String]

      val itsInferredFromFunctions = returnString()
      itsInferredFromFunctions shouldBe a [String]

      val itsInferredFromFunctionsWithoutType = returnTypesCanBeInferredToo()
      itsInferredFromFunctionsWithoutType shouldBe a [String]
    }

    it("can deferred until really needed - we call them lazy") {
      lazy val imLazy = new Date()
      Thread.sleep(2000)
      val imEager = new Date()
      Thread.sleep(2000)

      imEager.before(imLazy) shouldBe true // we declared it before, but the date is more recent
    }

    it("can also hold functions") {
      def namedFunction(): String = { "Hi, I'm named function!" }
      val namedFunctionHandle: () => String = namedFunction _//parameter less partially applied function
      val anonymousFunctionHandle: () => String = () => { "Hi, I'm anonymous!" }

      namedFunction() shouldBe "Hi, I'm named function!"
      anonymousFunctionHandle() shouldBe "Hi, I'm anonymous!"
    }

    it("variables holding functions can be passed as values ...") {
      var counter = 0
      def get = {
        counter = counter + 1
        42
      }

      def passByValue(x: Int) = {
        x + x + x
      }

      passByValue(get) shouldBe 126
      counter shouldBe 1
    }

    it("... or passed by name") {
      var counter = 0
      def get = {
        counter = counter + 1
        42
      }

      def passByName(x: => Int) = {
        x + x + x
      }

      passByName(get) shouldBe 126
      counter shouldBe 3
    }
  }
}
