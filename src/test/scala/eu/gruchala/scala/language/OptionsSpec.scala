package eu.gruchala.scala.language

import org.scalatest._
import org.scalatest.Matchers._
import org.scalatest.{GivenWhenThen, FunSpec}

class OptionsSpec extends FunSpec with GivenWhenThen {

  describe("Option[T]") {
    it("is a container time similar to Try[T], but instead of encapsulating success and failure it holds a return value ... ") {
      Some("Value of Any type")
    }

    it("... or it shows lack of return") {
      None
    }

    it("allows us to ditch null") {
      Option(null)
    }

    def sillyJavaStyleFindById(id: Int) = if(id % 2 == 0) id else null
    // the method above is dangerous, let's fix that!
    val existingValue = Option(sillyJavaStyleFindById(42))
    val missingValue = Option(sillyJavaStyleFindById(1))

    it("allows us to get the value in few different ways, some are risky ... ") {
      existingValue.get shouldBe 42
      println(s" missing VALYES $missingValue")
      intercept[NoSuchElementException] {
        missingValue.get
      }
    }

    it(" ... some are verbose ... ") {
      if(existingValue.isDefined){
        existingValue.get shouldBe 42
      }

      if(!missingValue.isEmpty) {
        fail("this will not happen")
      }
    }

    it("... others are handy ..."){
      existingValue.getOrElse(0) shouldBe 42
      missingValue.getOrElse(0) shouldBe 0
      missingValue.getOrElse({
        println("Sometimes you want to calculate a proper result")
        40 + 3
      }) shouldBe 43
      intercept[IllegalArgumentException] {
        missingValue.getOrElse(throw new IllegalArgumentException("Sometimes you just want to throw"))
      }
    }

    it("... or play nice together with functional style ...") {
      existingValue.map(x => 84) shouldBe Some(84)
      missingValue.map(x => 84) shouldBe None
    }

    it("also works great with patter matching") {
      existingValue match {
        case Some(s) => s shouldBe 42
        case None => fail("We won't get here")
      }

      val r = missingValue match {
        case Some(s) => fail("We won't get here")
        case None => 12 // or some other default
      }
      r shouldBe 12
    }

    it("it's a type safe way of handling missing values! say goodbye to NPE") {
      def typsafeFindById(id: Int) = if(id % 2 == 0) Some(id) else None

      val exists = typsafeFindById(42)
      val missing = typsafeFindById(41)
      //values are wrapped, so we have to unwrap them before using ...
      exists shouldBe a [Some[_]]
      missing shouldBe None
      // ... it might seem like unnecessary work, but forces us to handle cases where values are null
      (exists.getOrElse(1) + missing.getOrElse(1)) shouldBe 43
    }
  }
}
