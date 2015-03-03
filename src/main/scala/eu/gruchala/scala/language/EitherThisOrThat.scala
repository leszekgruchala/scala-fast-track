package eu.gruchala.scala.language

object EitherThisOrThat {
  
  //Either allows to return two completely different types
  //These can be used to return both normal types or exception (Left) and normal type (Right)
  
  val badValue = Left(new IllegalArgumentException)
  val goodValue = Right("The right result")

  badValue.isLeft //true
  badValue.isRight //false
  
  badValue.a //IllegalArgumentException
  goodValue.b //String
  badValue.left //LeftProjection[IllegalArgumentException, Nothing]
  badValue.right //RightProjection[IllegalArgumentException, Nothing]
  goodValue.left //LeftProjection[Nothing, String]
  goodValue.right //RightProjection[Nothing, String]

  goodValue.right map(_.size)

  //map is enough for for comprehension
  for (r <- goodValue.right) yield r //Right(The right result)

  val either: Either[Exception, Int] = try {
    Right("124123".toInt)
  } catch {
    case e: Exception => Left(e)
    //or any other type
    //case e: Exception => Left(0)
  }

  either match {
    case Left(l) => println(s"got left value $l")
    case Right(r) => println(s"got right value $r")
  }
  
  println(either match {
    case Right(b) => b
    case Left(a) => a
  })
}
