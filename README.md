Fast track to Scala
===========

Code examples here are a base for my presentation to Java fellows. This presentation is about to show Scala in possibly 
fast way allowing you to get your hands dirty. There is no aim to cover everything extensively, but give enough to understand the concept.

Whilst working on it, I got an idea this code can be a good introduction for people coming to Scala from other languages,
that's why you will find short comments explaining the code.
It's hard to find such concise resources around the web so it's worthwhile to make one.
Maybe except cheat sheets but they are too short and you can't play with them!

Examples are run on test specification so install [SBT](http://www.scala-sbt.org/download.html) and from command line
run all tests with `tests` or single one with `~testOnly *.MethodsAndFunctionsSpec`

You can paste code to [REPL](http://www.scala-lang.org/download/install.html) too (with `:paste` command) or
 from sbt run with main app with `runMain eu.gruchala.scala.Main` and play around.

Feel free to join forces, update code, add more things. We can talk at [![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/leszekgruchala/scala-fast-track)

Covered
---------
 * definitions: Pure function, referentially transparent
 * argument pass by value/by name
 * function literals
 * val vs var vs def vs lazy val
 * equality
 * partial functions
 * partially applied functions
 * case classes
 * classes and companion objects
 * default values
 * value classes
 * objects
 * traits
 * multiple traits inheritance
 * function composition
 * pattern matching
 * recursion / tail recursion
 * for comprehensions
 * implicits (function arguments, functions, implicitly)
 * underscore use cases
 * Future
 * Try
 * Either
 * type variances

Missing
---------
 * Promise
 * More functional examples
 * you name it
 
 More advence topics covered in another repo: [typelevel-programming-scala](https://github.com/leszekgruchala/typelevel-programming-scala)
 
 
License
---------
MIT License
