name := "OAuth"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  guice
) ++ Dependencies.oauthDependencies

lazy val root = project.in(file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(JavaAppPackaging)
  .settings(
    Dependencies.commonSettings
  )