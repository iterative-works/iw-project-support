package works.iterative.mill

import mill._
import mill.scalalib._
import mill.scalalib.scalafmt._

/**
 * Standard Scala module trait for Iterative Works projects using Mill.
 * This trait sets up standard compiler options and configuration.
 */
trait IWScalaModule extends ScalaModule with ScalafmtModule {
  // Default to Scala 3.6.3 as in SBT
  def scalaVersion = "3.6.3"
  
  // Default compiler options
  def scalacOptions = super.scalacOptions() ++ (
    if (scalaVersion().startsWith("2.")) {
      Seq(
        "-Xfatal-warnings",
        "-deprecation",
        "-feature",
        "-unchecked",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-Wunused:imports"
      )
    } else {
      Seq(
        "-Xfatal-warnings",
        "-deprecation",
        "-feature",
        "-unchecked",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-Wunused:imports"
      )
    }
  )
  
  // Enable SemanticDB for tooling
  def semanticDbOptions = Seq(
    "-Xsemanticdb"
  )
  
  // Override scalacOptions to include SemanticDB options
  override def scalacOptions = super.scalacOptions() ++ semanticDbOptions
  
  // Test configuration
  trait IWTests extends ScalaTests with TestModule.Utest with ScalafmtModule {
    def ivyDeps = super.ivyDeps() ++ Agg(
      ivy"com.lihaoyi::utest:0.8.2"
    )
  }
}