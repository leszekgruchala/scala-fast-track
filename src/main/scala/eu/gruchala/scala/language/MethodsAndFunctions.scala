package eu.gruchala.scala.language

trait MethodsAndFunctions {

  object Definition {

    //method (procedure more strictly due to no return value/Unit), side effects
    def pr(x: Int): Unit = println(x)

    //method with inferred return value
    def square(x: Int) = x * x

    //function - can be passed around, first class citizen, pure function, referentially transparent
    val f = (x: Int) => {
      println(s"Number of $x")
      x // no return keyword
    } //f: Int => Int = <function1> // Function1
  }

  object Passing {
    import Definition._

    1 to 10 map f

    def passFunc(x: Int => Int) = x(4)

    passFunc(square) // 16
    passFunc(f) //prints on console 4 and returns 4

    //function literal (anonymous)
    passFunc((x: Int) => x * x - x)
  }

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

  object PartialFunctions {

    val sample = 1 to 10
    val isEven: PartialFunction[Int, String] = {
      case x if x % 2 == 0 => x + " is even"

    }
    // the method collect can use isDefinedAt to select which members to collect
    val evenNumbers = sample collect isEven

    val isOdd: PartialFunction[Int, String] = {
      case x if x % 2 == 1 => x + " is odd"

    }
    // the method orElse allows chaining another partial function to handle
    // input outside the declared domain
    val numbers = sample map (isEven orElse isOdd)
  }

  object PartiallyAppliedFunctions {

    def calculateTaxes(price: Double, tax: Double) = price + (price * tax)

    //need to define placeholder with type
    val countForPL = calculateTaxes(_: Double, 0.23)
    val countForUK = calculateTaxes(_: Double, 0.20)

    countForPL(3.12)
    countForPL(412.22)

    countForUK(23.63)
    countForUK(5.53)

  }

}