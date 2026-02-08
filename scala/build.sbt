ThisBuild / scalaVersion := "3.3.7"
ThisBuild / organization := "com.fr.crafters-lyon"

lazy val ugly_molkky = (project in file("."))
  .settings(
    name := "Ugly_molkky;",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test
  )
