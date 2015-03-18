package eu.gruchala.scala

import java.time.LocalTime

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object RunningFutures {

  println(s"Starting at $now")

  val future_A = createFuture("A")
  val future_B = createFuture("B")

  val combined = for {
    f_A <- future_A
    f_B <- future_B
    f_C <- createFuture("C")
  } yield (f_A, f_B, f_C)

  combined onComplete {
    case Success(lines) => println(lines)
    case Failure(ex) => println(ex)
  }

  val sleep = 2000L

  def now = LocalTime.now()

  def createFuture(name: String) = Future {
    println(s"Starts future $name and sleeping $sleep ms")
    Thread.sleep(sleep)
    s"Returning result from Future $name at $now \n"
  }

  """
    |Sample result
    |
    |Starting at 13:44:12.946
    |Starts future B and sleeping 2000 ms
    |Starts future A and sleeping 2000 ms
    |Starts future C and sleeping 2000 ms
    |(Returning result from Future A at 13:44:14.963
    |,Returning result from Future B at 13:44:14.963
    |,Returning result from Future C at 13:44:16.969)
    |
    |Explanation
    |Futures A and B starts immediately concurrently at the time of variable definition, then they both sleep.
    |After sleep time and value return, Future C is created and results of all three are yielded to another new `combined` Future.
    |Then we get results of combined futures with `onComplete` which checks both success and failure of a Future.
  """.stripMargin

  //previous for comprehension is equivalent to this
  """
    |future_A.flatMap(
    |  (f_A) => future_B.flatMap(
    |    (f_B) => Future("C").map(
    |    (f_C) => scala.Tuple3(f_A, f_B, f_C)))
    |)
  """.stripMargin
  //you can desugar for comprehension and others:
  //REPL - start with `scala -Xprint:parser`
  //file - `scalac -Xprint:parser Foo.scala`


  //Here everything is run sequentially, because each Future is created after previous one is done
  (for {
    q <- createFuture("Q")
    w <- createFuture("W")
    e <- createFuture("E")
  } yield (q, w, e)) foreach println

  """
    |Sample result
    |
    |Starts future Q and sleeping 2000 ms
    |Starts future W and sleeping 2000 ms
    |Starts future E and sleeping 2000 ms
    |(Returning result from Future Q at 15:58:53.732
    |,Returning result from Future W at 15:58:55.733
    |,Returning result from Future E at 15:58:57.738)
  """.stripMargin

  //Here after successful result of Future F, Futures W and E are run concurrently
  (for {
    q <- createFuture("F")
    wF = createFuture("W")
    eF = createFuture("E")
    w <- wF
    e <- eF
  } yield (q, w, e)) foreach println

  """
    |Sample result
    |
    |Starts future F and sleeping 2000 ms
    |Starts future W and sleeping 2000 ms
    |Starts future E and sleeping 2000 ms
    |(Returning result from Future F at 16:06:43.542
    |,Returning result from Future W at 16:06:45.544//same time
    |,Returning result from Future E at 16:06:45.544)//same time
  """.stripMargin

}
