addSbtPlugin("org.foundweekends.giter8" %% "sbt-giter8" % "0.16.2")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.12.0")
addSbtPlugin("com.github.sbt" % "sbt-release" % "1.4.0")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.12.1")
libraryDependencies += {
    "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value
}
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.6.4")
