package eu.gruchala.scala.language.implicits

import scala.language.higherKinds

object Implicitly {

  //implicitly looks for implicit value of given type
  class A(val a: String)
  implicit val a = new A("let's roll")

  class B {
    def doStuff = {
      val a = implicitly[A]
      println(s"Found secret message: ${a.a}")
    }
  }
  new B().doStuff //Found secret message: let's roll
}
