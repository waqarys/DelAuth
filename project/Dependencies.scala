import sbt._

object Dependencies {

  val akkaVersion = "2.6.4"
  val circeVersion = "0.12.0"

  lazy val ws = "com.typesafe.play" %% "play-ahc-ws" % "2.8.1"
  lazy val inject = "javax.inject" % "javax.inject" % "1"
  lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  lazy val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion

  lazy val circeCore ="io.circe" %% "circe-core" % circeVersion
  lazy val circeGenericExtra = "io.circe" %% "circe-generic-extras" % circeVersion
  lazy val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
  //lazy val circeJava8 = "io.circe" % "circe-java8" % "0.12.0-M1"
  lazy val circeLiteral = "io.circe" %% "circe-literal" % circeVersion
  lazy val circeParser = "io.circe" %% "circe-parser" % circeVersion

  //lazy val playCirce = "play-circe" %% "play-circe" % "2608.4"
  lazy val playCirce = "com.dripower" %% "play-circe" % "2812.0"

  lazy val typesafeConfig = "com.typesafe" % "config" % "1.3.1"

  val scalaTest = "org.scalatest" % "scalatest" % "1.3"

  val commonDependencies: Seq[ModuleID] = Seq(scalaTest % Test)
  val circeDependencies: Seq[ModuleID] = Seq(circeCore, circeGenericExtra, circeGeneric, circeLiteral, circeParser, playCirce)
  val typesafeDependencies: Seq[ModuleID] = Seq(typesafeConfig)

  val oauthDependencies: Seq[ModuleID] = Seq(ws,
    inject, akkaStream, akkaSlf4j) ++ commonDependencies ++ circeDependencies ++ typesafeDependencies
}