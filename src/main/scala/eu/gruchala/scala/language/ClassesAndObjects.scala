package eu.gruchala.scala.language

trait ClassesAndObjects {

  //value classes / at compile time it's an object but at runtime it's a String, saves heap allocation
  case class Msisdn(number: String) extends AnyVal
  class Wrapper(val number: String) extends AnyVal

  //  Benefits of case classes:
  //  - immutable, by default constructor treat parameters as `val`s
  //  - build in toString, equals, hashcode
  //  - no need to use 'new' keyword
  //  - build in getters for fields (or with setters if fields defined with 'var')
  //  - automatically created companion object of the same name with apply/unapply (extractors (pattern matching))
  case class Money(value: Double, currency: String)
  val m = Money(//named arguments, order doesn't matter
    value = 12.3,
    currency = "PLN"
  )
  m.value
  Money(12.3, "PLN") == Money(12.3, "PLN")//true, checks values, equals method out of the box
  Money(12.3, "PLN").eq(Money(12.3, "PLN"))//false

  //default values
  class Producer(
    msg: String = "hello!",
    name: String = getName()
  )
  def getName() = "Name"

  //val adds public getters, var public getter/setters, no val/var private fields
  class Car(val name: String, val producer: String) {//constructor

    override def toString: String = s"name: $name \n producer: $producer"

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
  object Car {//companion object
    def apply(name: String, producer: String) = new Car(name, producer)
    def apply(name: String) = new Car(name, "FSO")
    def unapply(car: Car): Option[(String, String)] = Some((car.name, car.producer))
  }
  object audiCar {
    def unapply(car: Car): Boolean = car.producer == "AUDI"
  }

  //pattern matching
  Car("Polonez") match {
    case Car(name, "FSO") => println(s"Only FSO cars allowed here of name $name") //result
    case Car(name, producer) => println(s"We've got car named $name by $producer")
  }
  Car("M5", "BMW") match {
    case Car(name, "BMW") => println(s"Only BMW cars allowed here of name $name") //result
    case Car(name, producer) => println(s"We've got car named $name by $producer")
  }
  Car("Q7", "AUDI") match {
    case Car(name, "FSO") => println(s"Only FSO cars allowed here of name $name")
    case car @ audiCar() => println(s"Only Audi cars! We've got car named ${car.name} by ${car.producer}") //result
    case _ => println("Don't care!") //anything else
  }
  Car("Q7", "AUDI") == Car("Q7", "AUDI")//true (checks values, equals definition needed)
  Car("Q7", "AUDI").eq(Car("Q7", "AUDI"))//checks references to objects


  //constructors
  class WrappedOver(text: String, num: Int) {//main constructor
    //body of the constructor, any vals will be initialized immediately
    def this(num: Int) {//secondary constructor
      this("Something initial", num)
    }
  }


  //singleton, no need to use 'new' keyword
  //objects are independent entities, e.g.they can be used as method arguments, as target for implicit conversions, and case objects in pattern matching...
  //objects can inherit from classes or traits
  //an object has its own type
  //objects can restrict the access of its members
  object SuperService {
    def buildSuperStuff = ???
  }
  SuperService.buildSuperStuff

}
