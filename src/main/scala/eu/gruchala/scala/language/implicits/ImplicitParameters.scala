package eu.gruchala.scala.language.implicits

import scala.language.implicitConversions

object ImplicitParameters {

  class Prefix(prefix: String)

  class Enrichment(a: String) {
    def welcome(implicit prefix: Prefix) = prefix + a
  }

  implicit def stringToEnrichment(a: String): Enrichment = new Enrichment(a)

  implicit val defaultPrefix = new Prefix("Woohooo! ")

  println("JUG".welcome)//Woohooo! JUG

}
