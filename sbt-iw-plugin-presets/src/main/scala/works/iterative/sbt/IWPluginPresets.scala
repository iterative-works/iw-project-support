package works.iterative.sbt

import sbt._
import sbt.Keys._

object IWPluginPresets extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    val addIWProjects: Def.Setting[_] = addSbtPlugin(
      "works.iterative.sbt" % "sbt-iw-projects" % BuildInfo.version
    )
    val addScalaJSBundler: Def.Setting[_] =
      addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.20.0")
    val addWebScalaJSBundler: Def.Setting[_] =
      addSbtPlugin("ch.epfl.scala" % "sbt-web-scalajs-bundler" % "0.20.0")
    val addWebScalaJS: Def.Setting[_] =
      addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.2.0")
    val addScalablyTypedConverter: Seq[Def.Setting[_]] = Seq(
      resolvers += Resolver.bintrayRepo("oyvindberg", "converter"),
      addSbtPlugin(
        "org.scalablytyped.converter" % "sbt-converter" % "1.0.0-beta32"
      )
    )
    val addTzdb: Def.Setting[_] =
      addSbtPlugin("io.github.cquiroz" % "sbt-tzdb" % "1.0.1")
    val addLocales: Def.Setting[_] =
      addSbtPlugin("io.github.cquiroz" % "sbt-locales" % "2.6.0")
    val addScalaJS: Def.Setting[_] =
      addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.7.0")
    val addScalaJSCrossproject: Def.Setting[_] =
      addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.0.0")
    val addSbtScalaJSMap: Def.Setting[_] =
      addSbtPlugin(
        "com.thoughtworks.sbt-scala-js-map" % "sbt-scala-js-map" % "4.0.0"
      )
    val addScalaJSSupport: Seq[Def.Setting[_]] = Seq(
      addScalaJS,
      addScalaJSCrossproject,
      addSbtScalaJSMap,
      addTzdb,
      addLocales
    ) ++ addScalablyTypedConverter
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
    addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.1.18"),
    addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.2"),
    addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.8.1"),
    addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "1.0.1"),
    addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.28"),
    addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.10.0"),
    addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.0.0")
  )
}
