package eu.gruchala.scala.language

import scala.annotation.tailrec

object Recursion {

  //For recursive functions, one needs to define return type

  //recursion with pattern matching
  def sum(l: List[Int]): Int = l match {
    case head :: tail => head + sum(tail)
    case Nil => 0
  }

  def drop[A](l: List[A], n: Int): List[A] = {
    //tail recursion, possible if the call to recursive function is the last step
    @tailrec //assures tail call optimisation, prevents from StackOverflow errors, each iteration is calculated immediately
    def loop(i: Int, list: List[A]): List[A] = list match {
      case head :: tail => if (i == 0) tail else loop(i - 1, tail)
      case Nil => list
    }
    loop(n, l)
  }

}
