package eu.gruchala.scala.language.implicits

object ImplicitConversion {

  import scala.language.implicitConversions

  class RichString(a: String) {
    implicit def stringToInt(value: String): Int = Integer.valueOf(value)

    def *(b: Int): Int = b * a //will not compile without implicit
  }
  println(new RichString("3") * 2) // normally error, now 6

  class RicherString(a: String) {
    def scream = a.toUpperCase
  }
  implicit def enrichString(b: String): RicherString = new RicherString(b) //method name doesn't matter, types matters
  println("quiet".scream)//new method added to String => QUIET

  //Scala has RichInt, RichBoolean etc
}

