package eu.gruchala.scala.language

object Options {
  
  //Option - wrapper, monad
  val maybeName: Option[String] = Some("Jerry")
  maybeName match {
    case Some(x) => println(x)
    case None => println("No value")
  }
  //same as
  maybeName map println getOrElse "No value" //Jerry

  //only success case
  maybeName.foreach(println) //Jerry
  None foreach println //no printing
}
