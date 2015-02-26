package eu.gruchala.scala.language.traits

object Inheritance {

  trait Animal {
    def feature() = println("An animal will do anything to just eat.")
  }
  
  trait Instinct extends Animal {
    override def feature() = {
      super.feature()
      println("I eat, therefore I am!")
    }
  }

  trait Earthbound extends Animal {
    override def feature() = {
      super.feature()
      println("An earthbound animal will try to run fast to get some food.")
    }
  }

  trait Flying extends Animal {
    override def feature() = {
      super.feature()
      println("A flying animal will fly very fast to get some food.")
    }
  }

  trait Small extends Animal {
    override def feature() = {
      super.feature()
      println("I am small animal!")
    }
  }

  class Squirrel extends Earthbound with Flying with Small with Instinct {
    override def feature() = {
      super.feature()
      println("I can run and jump from one tree to another to get food!")
    }
  }

  val s = new Squirrel
  s.feature()
  """ RESULT:
    |An animal will do anything to just eat.                      //Animal
    |An earthbound animal will try to run fast to get some food.  //Earthbound
    |A flying animal will fly very fast to get some food.         //Flying
    |I am small animal!                                           //Small
    |I eat, therefore I am!                                       //Instinct
    |I can run and jump from one tree to another to get food!     //Squirrel
  """.stripMargin

  class Squirrel_2 extends Earthbound with Small with Flying {
    override def feature() = {
      super.feature()
      println("I can run and jump from one tree to another to get food!")
    }
  }

  val s_2 = new Squirrel_2
  s_2.feature()
  """ RESULT
    |An animal will do anything to just eat.
    |An earthbound animal will try to run fast to get some food.
    |I am small animal!
    |A flying animal will fly very fast to get some food.
    |I can run and jump from one tree to another to get food!
  """.stripMargin

  //First gets invoked common base type, then traits get invoked from left to right

}