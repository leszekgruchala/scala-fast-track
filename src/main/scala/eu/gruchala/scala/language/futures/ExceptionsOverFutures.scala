package eu.gruchala.scala.language.futures

import scala.concurrent.Future
import scala.util.{Failure, Success}

object ExceptionsOverFutures {

  import scala.concurrent.ExecutionContext.Implicits.global

  val successfulFuture = Future.successful("I worked well")//returns successfully completed Future, no new thread computation

  val failedFuture = Future.failed(new IllegalStateException("I worked not so well"))

  successfulFuture onComplete {
    case Success(result) => result
    case Failure(ex) => ex
  }

  successfulFuture onSuccess {//takes partial function
    case result => result
  }//I worked well

  successfulFuture onFailure {
    case exception => exception.printStackTrace()
  }//will even not get invoked

  successfulFuture foreach println //I worked well -  foreach is simpler version of `onSuccess`, gives value directly (function which takes direct result and returns Unit)
  failedFuture foreach println //no result, foreach is not even invoked
}
