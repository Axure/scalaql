import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "info.axurez",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies += scalaTest % Test,
    mainClass in (Compile, packageBin) := Some("example.HelloWorld"),
    mainClass in (Compile, run) := Some("example.HelloWorld")
  )


