package eu.gruchala.scala.language.traits

object Definition {

  trait Human {

    val text = "Say good"

    def afterWork()//implementation is optional

    def doTheRealWork(work: String) = println(s"Working hard(ly) at $work")
  }

  trait SuperPower {
    val power = "I can do anything with my imagination!"
  }

  trait Child extends Human

  trait Adult extends Human {
    override def afterWork() = println("Work harder of course!")
  }

//  class Person extends Child //does not compile without implementing afterWork()
  class Person extends Adult


  //self type allows for compile time check of dependency
  //here extending HappyChild requires dependency on SuperPower, i.e. CakePattern uses this
  trait HappyChild extends Human {
    self: SuperPower =>
    override def doTheRealWork(work: String) = println(s"This is what I do: $power")

    override def afterWork() = println("The same as before.")
  }

//  class SmallHappyChild extends HappyChild //does not compile, self type missing
  class SmallHappyChild extends HappyChild with SuperPower

}
