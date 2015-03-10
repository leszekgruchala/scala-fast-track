package eu.gruchala.scala.language

import org.scalatest._
import org.scalatest.Matchers._

class ClassesAndObjectsSpec extends FunSpec with GivenWhenThen {

  case class Money(value: Double, currency: String)

  describe("There are three types of classes") {

    describe("Value classes") {
      it("which save heap allocation") {
        //TODO learn better explanation
        //at compile time it's an object but at runtime it's a String
        //value class may not be a member of another class
        //case class Msisdn(number: String) extends AnyVal
        //class Wrapper(val number: String) extends AnyVal
      }
    }

    describe("Case classes") {
      it("""with benefits such as:
          |   - immutable, by default constructor treat parameters as `val`s
          |   - build in toString, equals, hashcode
          |   - no need to use 'new' keyword
          |   - build in getters for fields (or with setters if fields defined with 'var')
          |   - automatically created companion object of the same name with apply/unapply (extractors (pattern matching))""".stripMargin) {
        val m = Money(//named arguments, order doesn't matter
          value = 12.3,
          currency = "PLN"
        )
        m.value shouldBe 12.3
        assert(Money(12.3, "PLN") == Money(12.3, "PLN"))
        assert(!Money(12.3, "PLN").eq(Money(12.3, "PLN")))
      }
    }

    describe("Simple classes") {
      it("with no special power") {
        class Producer(msg: String, val name: String)
        val producer = new Producer("hello", "BMW")
        //producer.msg//does not compile, msg is private
        producer.name shouldBe "BMW"
      }

      it("and there is a way to define multiple constructors") {
        class WrappedOver(text: String, num: Int) {
          //body of the constructor, any vals will be initialized immediately
          def this(num: Int) {
            this("Something initial", num)
          }
        }
      }

      it("equals, hashcode and toString must be defined explicitly") {
        class Car(val name: String, val producer: String) {

          override def toString: String = s"name: $name \n producer: $producer"

          //helpful method to check if can equal subclasses
          def canEqual(other: Any): Boolean = other.isInstanceOf[Car]

          override def equals(other: Any): Boolean = other match {
            case that: Car =>
              (that canEqual this) &&
                name == that.name &&
                producer == that.producer
            case _ => false
          }

          override def hashCode(): Int = {
            val state = Seq(name, producer)
            state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
          }
        }
      }

      it("for patter matching extractors are needed") {
        class Car(val name: String, val producer: String)
        object Car {//companion object
          def apply(name: String, producer: String) = new Car(name, producer)
          def apply(name: String) = new Car(name, "FSO")
          def unapply(car: Car): Option[(String, String)] = Some((car.name, car.producer))
        }
        object audiCar {
          def unapply(car: Car): Boolean = car.producer == "AUDI"
        }

        Car("Polonez") match {
          case Car(name, "FSO") => name shouldBe "Polonez"
          case Car(name, producer) => fail("Not used now")
        }
        Car("M5", "BMW") match {
          case Car(name, "BMW") => name shouldBe "M5"
          case Car(name, producer) => fail("Not used now")
        }
        Car("Q7", "AUDI") match {
          case Car(name, "FSO") => fail("Not used now")
          case car @ audiCar() =>
            car.name shouldBe "Q7"
            car.producer shouldBe "AUDI"

          case _ => fail("Default case, not used now")
        }
      }
    }

    describe("There are Singletons too") {
      it("""with number of benefits:
          |   - singleton, no need to use 'new' keyword
          |   - objects are independent entities, e.g. they can be used as method arguments, as target for implicit conversions, and case objects in pattern matching
          |   - objects can inherit from classes or traits
          |   - an object has its own type
          |   - objects can restrict the access of its members""".stripMargin) {
        object SuperService {
          def buildSuperStuff = "smth"
        }
        SuperService.buildSuperStuff shouldBe "smth"
      }
    }

    describe("Default parameters") {
      it("can have classes, case classes and value classes") {
        class Producer(
          msg: String = "hello!",
          name: String = getName
        )
        def getName = "Name"
        case class Money(value: Double = 1L)
        //class Wrapper(val number: String = "ASD") extends AnyVal
      }

      it("including methods") {
        def getName(by: String = "John") = s"Name: $by"

        getName() shouldBe "Name: John"
      }
    }
  }
}