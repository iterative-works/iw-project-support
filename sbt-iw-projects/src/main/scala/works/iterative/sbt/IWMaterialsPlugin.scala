package works.iterative.sbt

import sbt._
import sbt.Keys._
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import _root_.io.github.sbt.tzdb.TzdbPlugin
import _root_.io.github.sbt.tzdb.TzdbPlugin.autoImport._
import locales.LocalesPlugin
import locales.LocalesPlugin.autoImport._

object IWMaterialsPlugin extends AutoPlugin {
  override def trigger: PluginTrigger = allRequirements
  object autoImport {
    val IWVersions = IWMaterialsVersions
    val IWDeps = IWMaterialsDeps
  }
}