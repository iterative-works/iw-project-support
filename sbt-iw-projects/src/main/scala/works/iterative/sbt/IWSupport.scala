package works.iterative.sbt

import sbt._
import sbt.Keys.libraryDependencies
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._

trait IWSupport {
  val supportOrg = "works.iterative.support"
  def supportName(name: String): String = s"iw-support-$name"

  class Support(version: String) {
    def supportMod(name: String): ModuleID =
      supportOrg %% supportName(name) % version
    def supportLib(name: String): Def.Setting[_] =
      libraryDependencies += supportMod(name)

    object modules {
      def core: ModuleID = supportMod("core")
      def codecs: ModuleID = supportMod("codecs")
      def tapir: ModuleID = supportMod("core")
      def mongo: ModuleID = supportMod("mongo")
      def `akka-persistence`: ModuleID = supportMod("akka-persistence")
      def ui: ModuleID = supportMod("ui")
      def `ui-model`: ModuleID = supportMod("ui-model")
    }

    object libs {
      def core: Def.Setting[_] =
        libraryDependencies += supportOrg %%% supportName("core") % version
      def codecs: Def.Setting[_] =
        libraryDependencies += supportOrg %%% supportName("codecs") % version
      def tapir: Def.Setting[_] =
        libraryDependencies += supportOrg %%% supportName("tapir") % version
      def mongo: Def.Setting[_] = supportLib("mongo")
      def `akka-persistence`: Def.Setting[_] = supportLib("akka-persistence")
      def ui: Def.Setting[_] =
        libraryDependencies += supportOrg %%% supportName("ui") % version
      def `ui-model`: Def.Setting[_] =
        libraryDependencies += supportOrg %%% supportName("ui-model") % version
      def `ui-components`: Def.Setting[_] =
        libraryDependencies += supportOrg %%% supportName(
          "ui-components"
        ) % version
    }
  }

  object support extends Support(IWMaterialsVersions.support)
}
