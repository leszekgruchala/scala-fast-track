package eu.gruchala.scala.language

object Variables {

  //cannot be reassigned
  val immutable = "hello"
  //collections are immutable by default, cannot change content
  val immutableL = List(1, 2, 3)
//  immutableL = List("asd")
//  immutableL += 4
  val newList = 3 :: immutableL//prepends element, produces new list, equivalent to immutableL.::(3)
  //Method :: ends with colon, which makes it right-associative

  var mutableVar = "hello"
  mutableVar = "hello hello"

  //can change content
  val mutableList = scala.collection.mutable.MutableList(1, 2, 3)
  mutableList += 1

  val mutableMap = scala.collection.mutable.Map(1 -> "one")
  mutableMap += 2 -> "two"

  //see difference in REPL
  lazy val lazyScream = () => "lazy AAAAAA!"
  val scream = () => "AAAAAA!"

  object Evaluation {
    //val evaluates when defined
    val test: () => Int = {
      val r = util.Random.nextInt()
      () => r
    }
    test() // Int = -1049057402
    test() // Int = -1049057402 - same result

    //def evaluates with each invocation, returns function
    def testDef: () => Int = {
      val r = util.Random.nextInt()
      () => r
    }

    //need to use () to invoke function
    testDef() // Int = -240885810
    testDef() // Int = -1002157461 - new result

    //simple method, returns value
    def testDef2: Int = util.Random.nextInt()

    testDef2 //no need to use (), because definition does not use it
    testDef2
  }

  object PassByValuePassByName {

    def justSaying() = {
      println("Saying")
      44
    }

    def passByValue(x: Int) = {
      println("x1=" + x)
      println("x2=" + x)
    }

    passByValue(justSaying())

    def passByName(x: => Int) = {
      println("x1=" + x)
      println("x2=" + x)
    }

    passByName(justSaying())
  }

}
