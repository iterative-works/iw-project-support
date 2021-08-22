ThisBuild / version := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(name := "$name$")
  // Auto activates for all projects, but make sure we have required dependencies
  .enablePlugins(FFScalaProjectPlugin)
