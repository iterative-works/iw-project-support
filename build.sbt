Global / semanticdbEnabled := true

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "works.iterative"

lazy val `sbt-iw-plugin-presets` = (project in file("sbt-iw-plugin-presets"))
  .enablePlugins(SbtPlugin)
  .disablePlugins(Giter8Plugin)
  .settings(
    organization := "works.iterative.sbt",
    name := "sbt-iw-plugin-presets",
    description := "Iterative Works SBT project plugin presets",
    scriptedLaunchOpts := {
      scriptedLaunchOpts.value ++
        Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false
  )

lazy val `sbt-support` = (project in file("."))
  // .enablePlugins(ScriptedPlugin)
  .settings(
    name := "iw-project-support",
    Test / test := {
      val _ = (Test / g8Test).toTask("").value
    },
    /*
    scriptedLaunchOpts ++= List(
      "-Xms1024m",
      "-Xmx1024m",
      "-XX:ReservedCodeCacheSize=128m",
      "-Xss2m",
      "-Dfile.encoding=UTF-8"
    ),
     */
    resolvers += Resolver.url(
      "typesafe",
      url("https://repo.typesafe.com/typesafe/ivy-releases/")
    )(Resolver.ivyStylePatterns)
  )
  .aggregate(`sbt-iw-plugin-presets`)
