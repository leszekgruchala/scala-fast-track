package eu.gruchala.scala.language.traits

object Inheritance {

  trait Animal {
    def feature() = println("An animal will do anything to just eat.")
  }
  
  trait Instinct extends Animal {
    override def feature() = println("I eat, therefore I am!")
  }

  trait Earthbound extends Animal {
    override def feature() = println("An earthbound animal will try to run fast to get some food.")
  }

  trait Flying extends Animal {
    override def feature() = println("A flying animal will fly very fast to get some food.")
  }

  trait Small extends Animal {
    override def feature() = println("I am small animal!")
  }

  //Traits are invoked from right to left
  class Squirrel extends Earthbound with Flying with Small with Instinct
  (new Squirrel).feature()
  """ RESULT:
    |I eat, therefore I am!     //Instinct
  """.stripMargin

  class Squirrel_2 extends Instinct with Small with Flying
  (new Squirrel_2).feature()
  """ RESULT:
    |A flying animal will fly very fast to get some food.     //Flying
  """.stripMargin

  class Squirrel_3 extends Earthbound with Small with Flying {
    override def feature() = {
      super.feature()
      println("I can run and jump from one tree to another to get food!")
    }
  }

  (new Squirrel_3).feature()
  """ RESULT
    |An animal will do anything to just eat.                    //Animal
    |I can run and jump from one tree to another to get food!   /Squirell_3
  """.stripMargin
}