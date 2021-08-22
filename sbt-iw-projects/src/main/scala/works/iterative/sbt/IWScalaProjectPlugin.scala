package works.iterative.sbt

import sbt._
import sbt.Keys._

import scalafix.sbt.ScalafixPlugin
import scalafix.sbt.ScalafixPlugin.autoImport._
import org.scalafmt.sbt.ScalafmtPlugin
import org.scalafmt.sbt.ScalafmtPlugin.autoImport._

object IWScalaProjectPlugin extends AutoPlugin {
  override def requires: Plugins = ScalafixPlugin && ScalafmtPlugin

  override def trigger: PluginTrigger = allRequirements

  object autoImport {
    val scala2Version = "2.13.6"
    val scala3Version = "3.0.1"
  }

  import autoImport._

  override def buildSettings: Seq[Def.Setting[_]] = Seq(
    scalaVersion := scala2Version,
    // enable SemanticDB
    semanticdbEnabled := true,
    // use Scalafix compatible version,
    semanticdbVersion := scalafixSemanticdb.revision,
    versionScheme := Some("early-semver")
  )

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    // Do not buffer logging in tests
    Test / logBuffered := false,
    // Do I need this still for Metals?
    // scalacOptions += s"-P:semanticdb:sourceroot:${(ThisBuild / baseDirectory).value}",
    scalafmtOnCompile := true
  )
}
