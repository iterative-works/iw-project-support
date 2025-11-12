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
        val scala2Version = "2.13.17"
        val scala3LTSVersion = "3.3.7"
        val scala3Version = "3.7.4"
        def publishToIW: Seq[Def.Setting[?]] = Seq(
            ThisBuild / publishTo := {
                val nexus = "https://nexus.e-bs.cz/repository/maven-"
                if (isSnapshot.value)
                    Some("snapshots" at nexus + "snapshots/")
                else
                    Some("releases" at nexus + "releases/")
            },
            ThisBuild / credentials ++= (for {
                username <- sys.env.get("EBS_NEXUS_USERNAME")
                password <- sys.env.get("EBS_NEXUS_PASSWORD")
            } yield Credentials(
                "Sonatype Nexus Repository Manager",
                "nexus.e-bs.cz",
                username,
                password
            )).toList
        )
        def resolveIW: Seq[Def.Setting[?]] = inThisBuild(
            List(
                resolvers += "e-BS Release Repository" at "https://nexus.e-bs.cz/repository/maven-releases/",
                resolvers += "e-BS Snapshot Repository" at "https://nexus.e-bs.cz/repository/maven-snapshots/"
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
