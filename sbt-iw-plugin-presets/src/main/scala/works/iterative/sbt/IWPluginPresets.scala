package works.iterative.sbt

import sbt._
import sbt.Keys._

object IWPluginPresets extends AutoPlugin {
  override def trigger = allRequirements

  val scalaJSCrossprojectVersion = "1.1.0"

  object autoImport {
    val addIWProjects: Seq[Def.Setting[_]] = Seq(
      addSbtPlugin(
        "works.iterative.sbt" % "sbt-iw-projects" % BuildInfo.version
      ),
      resolvers += "IW releases" at "https://dig.iterative.works/maven/releases",
      resolvers += "IW snapshots" at "https://dig.iterative.works/maven/snapshots"
    )
    val addScalaJSBundler: Def.Setting[_] =
      addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.20.0")
    val addWebScalaJSBundler: Def.Setting[_] =
      addSbtPlugin("ch.epfl.scala" % "sbt-web-scalajs-bundler" % "0.20.0")
    val addWebScalaJS: Def.Setting[_] =
      addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.2.0")
    val addScalablyTypedConverter: Def.Setting[_] =
      addSbtPlugin(
        "org.scalablytyped.converter" % "sbt-converter" % "1.0.0-beta37"
      )
    val addTzdb: Def.Setting[_] =
      addSbtPlugin("io.github.cquiroz" % "sbt-tzdb" % "3.0.0")
    val addLocales: Def.Setting[_] =
      addSbtPlugin("io.github.cquiroz" % "sbt-locales" % "3.2.0")
    val addScalaJS: Def.Setting[_] =
      addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.10.1")
    val addScalaJSCrossproject: Def.Setting[_] =
      addSbtPlugin(
        "org.portable-scala" % "sbt-scalajs-crossproject" % scalaJSCrossprojectVersion
      )
    val addSbtScalaJSMap: Def.Setting[_] =
      addSbtPlugin(
        "com.thoughtworks.sbt-scala-js-map" % "sbt-scala-js-map" % "4.1.1"
      )
    val addScalaJSSupport: Seq[Def.Setting[_]] = Seq(
      addScalaJS,
      addScalaJSCrossproject,
      addSbtScalaJSMap,
      addTzdb,
      addLocales,
      addScalablyTypedConverter
    )
    val addLagom: Def.Setting[_] =
      addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.6.5")
    val addPlay: Def.Setting[_] =
      addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.8")
    val addDockerCompose: Def.Setting[_] =
      addSbtPlugin("com.tapad" % "sbt-docker-compose" % "1.0.35")
    val addPlaySupport: Seq[Def.Setting[_]] = Seq(
      addPlay,
      addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.2"),
      addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.4")
    )
  }

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.6.1"),
    addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.4.1"),
    addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6"),
    addSbtPlugin("com.github.sbt" % "sbt-native-packager" % "1.9.9"),
    addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "1.0.2"),
    addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.10.1"),
    addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.10.0"),
    addSbtPlugin(
      "org.portable-scala" % "sbt-scalajs-crossproject" % scalaJSCrossprojectVersion
    )
  )
}
