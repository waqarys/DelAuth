name := "OAuth"

version := "0.1"

scalaVersion := "2.13.1"

//libraryDependencies ++= Dependencies.oauthDependencies

lazy val root = project.in(file("."))
  .enablePlugins(PlayScala)
  .settings(
    libraryDependencies ++= Dependencies.oauthDependencies
  )