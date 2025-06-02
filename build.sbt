Global / semanticdbEnabled := true
Global / semanticdbVersion := scalafixSemanticdb.revision

ThisBuild / organization := "works.iterative"
ThisBuild / versionScheme := Some("strict")

inThisBuild(
  List(
    resolvers ++= Seq(
      "e-BS Snapshot Repository" at "https://nexus.e-bs.cz/repository/maven-snapshots/",
      "e-BS Release Repository" at "https://nexus.e-bs.cz/repository/maven-releases/"
    )
  ) ++
    (for {
      username <- sys.env.get("EBS_NEXUS_USERNAME")
      password <- sys.env.get("EBS_NEXUS_PASSWORD")
    } yield credentials += Credentials(
      "Sonatype Nexus Repository Manager",
      "nexus.e-bs.cz",
      username,
      password
    )).toList
)

ThisBuild / publishTo := {
  val nexus = "https://nexus.e-bs.cz/repository/maven-"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "snapshots/")
  else
    Some("releases" at nexus + "releases/")
}

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
        addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.2"),
        addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.12.1"),
        addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.3.2"),
        addSbtPlugin("io.github.cquiroz" % "sbt-tzdb" % "4.3.0"),
        addSbtPlugin("io.github.cquiroz" % "sbt-locales" % "4.4.0"),
        addSbtPlugin("org.typelevel" % "sbt-tpolecat" % "0.5.1"),
        addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.19.0")
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
