package eu.gruchala.scala.language

object Functional {


  val l = List(1, 2, 3, 4, 5, 6, 7, 8)
  val newList = 3 :: l //prepends element, produces new list, equivalent to immutableL.::(3)

  //foreach faster then map because returns Unit
  l.foreach(elem => println(elem))
  l.foreach(println(_))
  l foreach println//infix notation, use only with purely functional methods (no side effects)

  //maps/changes element of the collection to another collection (even with different type)
  l map(5 * _)

  //curring - helpful for parameter separation
  //first parameter is initial value, second function which gets (current element, accumulator) and returns new element same type as current
  l.foldLeft(0)((elem, acc) => elem + acc)
  l.foldLeft(0)(_ + _) 
  l.sum


  case class P(name: String, age: Int)
  val p = List(P("John", 10), P("Jimmy", 15), P("Jane", 22), P("Janice", 18))
  val namesUnder20 = p withFilter(_.age >= 20) map(_.name)//Jane

}
