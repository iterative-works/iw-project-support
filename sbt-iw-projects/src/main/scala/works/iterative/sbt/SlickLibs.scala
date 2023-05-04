package works.iterative.sbt

import sbt._
import sbt.Keys.libraryDependencies

trait SlickLibs {
  object slick {
    val V = IWMaterialsVersions.slick
    val org = "com.typesafe.slick"

    object modules {
      lazy val slick: ModuleID = org %% "slick" % V
      lazy val hikaricp: ModuleID = org %% "slick-hikaricp" % V
      lazy val default: Seq[ModuleID] = Seq(slick, hikaricp)
      def default(mod: ModuleID => ModuleID): Seq[ModuleID] =
        modules.default.map(mod)
    }

    object libs {
      lazy val slick: Def.Setting[_] = libraryDependencies += modules.slick
      lazy val hikaricp: Def.Setting[_] =
        libraryDependencies += modules.hikaricp
      lazy val default: Def.Setting[_] = libraryDependencies ++= modules.default
      def default(mod: ModuleID => ModuleID): Def.Setting[_] =
        libraryDependencies ++= modules.default(mod)
    }
  }
}
