Global / semanticdbEnabled := true
Global / semanticdbVersion := scalafixSemanticdb.revision

ThisBuild / organization := "works.iterative"
ThisBuild / versionScheme := Some("strict")

inThisBuild(
  List(
    publishTo := {
      val base = "https://dig.iterative.works/maven/"
      if (version.value.endsWith("SNAPSHOT"))
        Some("snapshots" at base + "snapshots")
      else Some("releases" at base + "releases")
    },
    credentials += {
      val username = sys.env.getOrElse("IW_USERNAME", "")
      val password = sys.env.getOrElse("IW_PASSWORD", "")
      Credentials(
        "GitBucket Maven Repository",
        "dig.iterative.works",
        username,
        password
      )
    }
  )
)

lazy val `sbt-iw-plugin-presets` = (project in file("sbt-iw-plugin-presets"))
  .enablePlugins(SbtPlugin, BuildInfoPlugin)
  .disablePlugins(Giter8Plugin)
  .settings(
    organization := "works.iterative.sbt",
    name := "sbt-iw-plugin-presets",
    description := "Iterative Works SBT project plugin presets",
    scriptedLaunchOpts := {
      scriptedLaunchOpts.value ++
        Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false,
    buildInfoKeys := Seq[BuildInfoKey](name, version),
    buildInfoPackage := "works.iterative.sbt"
  )

lazy val `sbt-iw-projects` = (project in file("sbt-iw-projects"))
  .enablePlugins(SbtPlugin)
  .disablePlugins(Giter8Plugin)
  .settings(
    organization := "works.iterative.sbt",
    name := "sbt-iw-projects",
    description := "Iterative Works SBT project support",
    scriptedLaunchOpts := {
      scriptedLaunchOpts.value ++
        Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false,
    addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.0"),
    addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.10.4"),
    addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.3.1"),
    addSbtPlugin("io.github.cquiroz" % "sbt-tzdb" % "4.2.0"),
    addSbtPlugin("io.github.cquiroz" % "sbt-locales" % "4.2.0"),
    addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.4.2")
  )

lazy val `iw-project-support` = (project in file("."))
  .enablePlugins(ScriptedPlugin)
  .settings(
    name := "iw-project-support",
    Test / test := {
      val _ = (Test / g8Test).toTask("").value
    },
    scriptedLaunchOpts ++= List(
      "-Xms1024m",
      "-Xmx1024m",
      "-XX:ReservedCodeCacheSize=128m",
      "-Xss2m",
      "-Dfile.encoding=UTF-8"
    ),
    resolvers += Resolver.url(
      "typesafe",
      url("https://repo.typesafe.com/typesafe/ivy-releases/")
    )(Resolver.ivyStylePatterns),
    publish / skip := true
  )
  .aggregate(`sbt-iw-plugin-presets`, `sbt-iw-projects`)
