package eu.gruchala.scala.language

import org.scalatest._
import org.scalatest.Matchers._

import scala.language.implicitConversions

class ImplicitsSpec extends FunSpec with GivenWhenThen {

  describe("Implicit conversions") {
    it("need special import") {
      import scala.language.implicitConversions
    }
    it("allows to enrich types") {
      class RichString(a: String) {
        implicit def stringToInt(value: String): Int = Integer.valueOf(value)

        def *(b: Int): Int = b * a //will not compile without implicit
      }
      new RichString("3") * 2 shouldBe 6

      class RicherString(a: String) {
        def scream = a.toUpperCase
      }
      implicit def enrichString(b: String): RicherString = new RicherString(b) //method name doesn't matter, types matters
      "quiet".scream shouldBe "QUIET"

      //Scala has RichInt, RichBoolean etc
    }
  }

  describe("Implicitly") {
    it("allows to look for needed type") {
      import implicits.Implicitly._ //don't want to work inside spec
      new B().doStuff shouldBe "Found secret message: let's roll"
    }
  }

  describe("Implicit parameters") {
    it("are helpful if one needs commonly used type without explicit need to be passed around") {
      class Prefix(prefix: String) {
        override def toString: String = prefix
      }

      class Enrichment(a: String) {
        def welcome(implicit prefix: Prefix) = prefix + a
      }

      implicit def stringToEnrichment(a: String): Enrichment = new Enrichment(a)

      implicit val defaultPrefix = new Prefix("Woohooo! ")

      "JUG".welcome shouldBe "Woohooo! JUG"
    }
  }
}