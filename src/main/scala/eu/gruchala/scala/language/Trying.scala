package eu.gruchala.scala.language

import scala.util.{Failure, Success, Try}

object Trying {
  
  //Try[T] returns either given type T (correct result) or an exception
  val letters = Try("cannot be number".toInt)
  val numbers = Try("1234123".toInt)
  
  val combined = letters flatMap(x => numbers.map(y => x * y))
  
  combined match {
    case Success(result) => println(s"Result: $result")
    case Failure(ex) => println(s"Something went wrong: $ex")
  }
  
  //Try has flatMap, map, foreach, withFilter so we can use for comprehension
  val forCombined = for {
    numFromLetter  <- letters
    numFromNumbers <- numbers
  } yield numFromLetter * numFromNumbers
  forCombined foreach println
  
  letters.isFailure // true
  letters.isSuccess // false
  
  numbers.get
  numbers.getOrElse(5)
  
  //like a map for exception, return just value
  numbers.recover {
    case e: NumberFormatException => "Please do not use letters as numbers!"
    case _ => "Things just sometimes blow up"
  }
  
  //like a flatMap for exception, return wrapped as Try
  numbers.recoverWith {
    case e: NumberFormatException => Try("Please do not use letters as numbers!")
    case _ => Try("Things just sometimes blow up")
  }

}
