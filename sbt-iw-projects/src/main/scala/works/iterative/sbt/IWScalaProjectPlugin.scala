package works.iterative.sbt

import sbt.*
import sbt.Keys.*

import scalafix.sbt.ScalafixPlugin
import scalafix.sbt.ScalafixPlugin.autoImport.*
import org.scalafmt.sbt.ScalafmtPlugin
import org.scalafmt.sbt.ScalafmtPlugin.autoImport.*

object IWScalaProjectPlugin extends AutoPlugin {
    override def requires: Plugins = ScalafixPlugin && ScalafmtPlugin

    override def trigger: PluginTrigger = allRequirements

    object autoImport {
        val scala2Version = "2.13.16"
        val scala3LTSVersion = "3.3.6"
        val scala3Version = "3.7.0"
        def publishToIW: Seq[Def.Setting[?]] = inThisBuild(
            List(
                publishTo := {
                    val base = "https://dig.iterative.works/maven/"
                    if (version.value.endsWith("SNAPSHOT")) {
                        Some("snapshots" at base + "snapshots")
                    } else Some("releases" at base + "releases")
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
        def resolveIW: Seq[Def.Setting[?]] = inThisBuild(
            List(
                resolvers += "IW releases" at "https://dig.iterative.works/maven/releases",
                resolvers += "IW snapshots" at "https://dig.iterative.works/maven/snapshots"
            )
        )
    }

    import autoImport.*

    override def buildSettings: Seq[Def.Setting[?]] = Seq(
        scalaVersion := scala3Version,
        // enable SemanticDB
        semanticdbEnabled := true,
        // use Scalafix compatible version,
        semanticdbVersion := scalafixSemanticdb.revision,
        versionScheme := Some("early-semver")
    )

    override def projectSettings: Seq[Def.Setting[?]] = Seq(
        // Do not buffer logging in tests
        Test / logBuffered := false
        // Do I need this still for Metals?
        // scalacOptions += s"-P:semanticdb:sourceroot:${(ThisBuild / baseDirectory).value}",
        // This is not recommended, as it slows down compilation.
        // scalafmtOnCompile := true
    )
}
