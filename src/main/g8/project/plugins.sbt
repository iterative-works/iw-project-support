lazy val root = (project in file(".")).dependsOn(iwPlugin)
lazy val iwPlugin = RootProject(file("../sbt-support"))
