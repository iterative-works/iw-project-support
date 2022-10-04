package works.iterative.sbt

import _root_.sbt._
import _root_.sbt.Keys._
import _root_.io.github.davidgregory084.TpolecatPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin

// Fix tpolecat's options leaving out scalajs for Scala 3, see https://github.com/typelevel/sbt-tpolecat/issues/102
object TpolecatFixPlugin extends AutoPlugin {
  override def requires = TpolecatPlugin && ScalaJSPlugin
  override def trigger = allRequirements
  override def projectSettings = Seq(
    scalacOptions ++= {
      if (scalaVersion.value.startsWith("3")) Seq("-scalajs") else Seq.empty
    }
  )
}
