package works.iterative.sbt

import sbt.*
import sbt.Keys.*

object IWPluginPresets extends AutoPlugin {
    override def trigger = allRequirements

    val scalaJSCrossprojectVersion = "1.3.2"

    object autoImport {
        val addIWProjects: Seq[Def.Setting[?]] = Seq(
            addSbtPlugin(
                "works.iterative.sbt" % "sbt-iw-projects" % BuildInfo.version
            ),
            resolvers += "e-BS Release Repository" at "https://nexus.e-bs.cz/repository/maven-releases/",
            resolvers += "e-BS Snapshot Repository" at "https://nexus.e-bs.cz/repository/maven-snapshots/"
        )
        val addScalablyTypedConverter: Def.Setting[?] =
            addSbtPlugin(
                "org.scalablytyped.converter" % "sbt-converter" % "1.0.0-beta44"
            )
        val addTzdb: Def.Setting[?] =
            addSbtPlugin("io.github.cquiroz" % "sbt-tzdb" % "4.3.0")
        val addLocales: Def.Setting[?] =
            addSbtPlugin("io.github.cquiroz" % "sbt-locales" % "4.5.0")
        val addScalaJS: Def.Setting[?] =
            addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.19.0")
        val addScalaJSCrossproject: Def.Setting[?] =
            addSbtPlugin(
                "org.portable-scala" % "sbt-scalajs-crossproject" % scalaJSCrossprojectVersion
            )
        val addSbtScalaJSMap: Def.Setting[?] =
            addSbtPlugin(
                "com.thoughtworks.sbt-scala-js-map" % "sbt-scala-js-map" % "4.1.1"
            )
        val addScalaJSSupport: Seq[Def.Setting[?]] = Seq(
            // TODO: remove after the scalajs-locales scala-xml dep will stop being in conflict with the rest
            // It already uses scala-xml 2.0.1, but the rest is still on 1.3.x
            evictionErrorLevel := Level.Warn,
            addScalaJS,
            addScalaJSCrossproject,
            addTzdb,
            addLocales,
            addScalablyTypedConverter
        )
        val addLagom: Def.Setting[?] =
            addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.6.7")
        val addPlay: Def.Setting[?] =
            addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.18")
        val addPlaySupport: Seq[Def.Setting[?]] = Seq(
            addPlay,
            addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "2.0.0"),
            addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "2.0.0")
        )
    }

    override def projectSettings: Seq[Def.Setting[?]] = Seq(
        addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.6.4"),
        addSbtPlugin("org.typelevel" % "sbt-tpolecat" % "0.5.2"),
        addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.4"),
        addSbtPlugin("com.github.sbt" % "sbt-native-packager" % "1.11.1"),
        // Do not add the sbt-git by default, needs extra setting for git worktrees and we don't use it usually
        // addSbtPlugin("com.github.sbt" % "sbt-git" % "2.0.1"),
        addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.14.2"),
        addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.13.1"),
        addSbtPlugin(
            "org.portable-scala" % "sbt-scalajs-crossproject" % scalaJSCrossprojectVersion
        )
    )
}
