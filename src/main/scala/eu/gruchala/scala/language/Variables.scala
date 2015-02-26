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

}
