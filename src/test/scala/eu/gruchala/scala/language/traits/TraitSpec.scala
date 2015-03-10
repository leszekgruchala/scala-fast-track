package eu.gruchala.scala.language.traits

import org.scalatest._
import org.scalatest.Matchers._

class TraitSpec extends FunSpec with GivenWhenThen {

  case class Person(name: String)

  describe("+++++++++++Trait") {
    it("is (a bit) like an Java interface") {
      trait Plain {
        def doSomething(): String // no implementation, just like Java
      }

      val anonymous = new Plain {
        override def doSomething(): String = "I'm a mystery"
      }
      anonymous.doSomething() shouldBe "I'm a mystery"
    }

    it("that you have to implement") {
      trait Plain {
        def talkToMe(): String // no implementation, just like Java
      }

      case class OrdinaryPerson(name: String) extends Plain {
        override def talkToMe(): String = s"Hi, I'm $name" // so let's implement it for this whole class
      }

      new OrdinaryPerson("Mike").talkToMe() shouldBe "Hi, I'm Mike"
    }

    it("but also has super powers ") {}
    it(" ... like ad hoc mixing into already created objects") {
      trait Plain {
        def talkToMe(): String
      }

      val norman = new Person("Norman Normal") with Plain {
        override def talkToMe(): String = "I'm a special snowflake ... mom said so" // let's add unique behaviour Norman
      }
      norman shouldBe a [Person with Plain]
      norman.talkToMe() shouldBe "I'm a special snowflake ... mom said so"
    }

    it(" ... or default implementations ") {
      trait Worker {
        def talkToMe(): String = "Work, work, work"
      }

      case class WorkingPerson(name: String) extends Worker

      new WorkingPerson("Joe").talkToMe() shouldBe "Work, work, work"
    }

    it("can add field requirements for classes mixing it") {
      trait Named {
        def name: String //class implementing this trait must have fields with matching names and types
        def surname: String
        def introduce(): String = s"Nice to meet you! I'm $name $surname"
      }

      class NamedPerson(override val name: String, override val surname: String) extends Named
      new NamedPerson("Johny", "Derp").introduce() shouldBe "Nice to meet you! I'm Johny Derp"
    }

    it(" ... or require to implement some interface") {
      trait Jedi
      trait ForceUser { self: Jedi => // can only be mixed into a Jedi
        def forceChoke = "I find your lack of lack of faith disturbing"
      }

      //take a closer look here we use `extends` once and then `with`
      case object DarthVader extends Jedi with ForceUser // case object, because there's only one true Darth Vader!
      DarthVader.forceChoke shouldBe "I find your lack of lack of faith disturbing"
    }

    it("allows us to do multiple inheritance (to some extend) without the diamond problem") {
      trait Hero {
        def useForce: String
      }

      trait Sith extends Hero {
        override def useForce: String = "Good, let the hate flow through you"
      }

      trait Wizard extends Hero {
        override def useForce: String = "You're a wizard"
      }

      class HogwartsJedi(name: String) extends Sith with Wizard
      new HogwartsJedi("Martin O").useForce shouldBe "You're a wizard"
    }

    it("it's the simplest way of sharing logic between different classes") {
      trait Named {
        def getName(): String = getClass.getSimpleName.split("\\$").takeRight(2).head // nested case classed like the below, have more complex names than it appears
      }

      case class Dog() extends Named
      case class Robot() extends Named

      new Dog().getName() shouldBe "Dog"
      new Robot().getName() shouldBe "Robot"
    }
  }
}
