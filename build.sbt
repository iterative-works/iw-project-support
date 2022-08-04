Global / semanticdbEnabled := true

ThisBuild / organization := "works.iterative"
ThisBuild / versionScheme := Some("strict")

inThisBuild(List(
	publishTo := {
	  val base = "https://dig.iterative.works/maven/"
	  if (version.value.endsWith("SNAPSHOT")) Some("snapshots" at base + "snapshots")
	  else                                    Some("releases"  at base + "releases")
	},
	credentials += Credentials(Path.userHome / ".sbt" / ".iw-credentials")
))

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
    addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.2"),
    addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.28"),
    addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.0.0"),
    addSbtPlugin("io.github.cquiroz" % "sbt-tzdb" % "3.0.0"),
    addSbtPlugin("io.github.cquiroz" % "sbt-locales" % "3.2.0")
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
