name := "scala-basics"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.lihaoyi" % "ammonite-repl" % "0.8.2" % "test" cross CrossVersion.full
)

initialCommands in (Test, console) := """ammonite.repl.Main.run("")"""
