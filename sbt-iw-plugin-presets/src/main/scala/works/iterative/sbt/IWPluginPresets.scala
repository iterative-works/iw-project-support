package works.iterative.sbt

import sbt._
import sbt.Keys._

object IWPluginPresets extends AutoPlugin {
  override def trigger = allRequirements

  val scalaJSCrossprojectVersion = "1.3.2"

  object autoImport {
    val addIWProjects: Seq[Def.Setting[_]] = Seq(
      addSbtPlugin(
        "works.iterative.sbt" % "sbt-iw-projects" % BuildInfo.version
      ),
      resolvers += "IW releases" at "https://dig.iterative.works/maven/releases",
      resolvers += "IW snapshots" at "https://dig.iterative.works/maven/snapshots"
    )
    val addScalaJSBundler: Def.Setting[_] =
      addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.21.1")
    val addWebScalaJSBundler: Def.Setting[_] =
      addSbtPlugin("ch.epfl.scala" % "sbt-web-scalajs-bundler" % "0.21.1")
    val addWebScalaJS: Def.Setting[_] =
      addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.2.0")
    val addScalablyTypedConverter: Def.Setting[_] =
      addSbtPlugin(
        "org.scalablytyped.converter" % "sbt-converter" % "1.0.0-beta42"
      )
    val addTzdb: Def.Setting[_] =
      addSbtPlugin("io.github.cquiroz" % "sbt-tzdb" % "4.2.0")
    val addLocales: Def.Setting[_] =
      addSbtPlugin("io.github.cquiroz" % "sbt-locales" % "4.2.0")
    val addScalaJS: Def.Setting[_] =
      addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.14.0")
    val addScalaJSCrossproject: Def.Setting[_] =
      addSbtPlugin(
        "org.portable-scala" % "sbt-scalajs-crossproject" % scalaJSCrossprojectVersion
      )
    val addSbtScalaJSMap: Def.Setting[_] =
      addSbtPlugin(
        "com.thoughtworks.sbt-scala-js-map" % "sbt-scala-js-map" % "4.1.1"
      )
    val addScalaJSSupport: Seq[Def.Setting[_]] = Seq(
      // TODO: remove after the scalajs-locales scala-xml dep will stop being in conflict with the rest
      // It already uses scala-xml 2.0.1, but the rest is still on 1.3.x
      evictionErrorLevel := Level.Warn,
      addScalaJS,
      addScalaJSCrossproject,
      addTzdb,
      addLocales,
      addScalablyTypedConverter
    ) ++ (if (file(".git").isFile) { // only add sbt-scala-js-map if we're in a git repo
            Seq(addSbtScalaJSMap)
          } else {
            Seq.empty
          })
    val addLagom: Def.Setting[_] =
      addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.6.7")
    val addPlay: Def.Setting[_] =
      addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.18")
    val addDockerCompose: Def.Setting[_] =
      addSbtPlugin("com.tapad" % "sbt-docker-compose" % "1.0.35")
    val addPlaySupport: Seq[Def.Setting[_]] = Seq(
      addPlay,
      addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.2"),
      addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.4")
    )
  }

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.6.4"),
    addSbtPlugin("org.typelevel" % "sbt-tpolecat" % "0.5.0"),
    addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.2"),
    addSbtPlugin("com.github.sbt" % "sbt-native-packager" % "1.9.16"),
    addSbtPlugin("com.github.sbt" % "sbt-git" % "2.0.1"),
    addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.11.1"),
    addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.11.0"),
    addSbtPlugin(
      "org.portable-scala" % "sbt-scalajs-crossproject" % scalaJSCrossprojectVersion
    )
  )
}
