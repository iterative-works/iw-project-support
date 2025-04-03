import mill._
import mill.scalalib._
import mill.scalalib.publish._
import mill.scalalib.scalafmt._
import mill.scalalib.TestModule.Utest

/** Main module for the mill-iw-support library. Built for Scala 2.13 which is
  * compatible with Mill.
  */
object core extends ScalaModule with ScalafmtModule with PublishModule {
  val millVersion = "0.12.10"

  def scalaVersion = "2.13.16"

  def artifactName = "mill-iw-support"

  def publishVersion = "0.1.0-SNAPSHOT"

  def pomSettings = PomSettings(
    description = "Iterative Works Mill Support Library",
    organization = "works.iterative",
    url = "https://github.com/iterative-works/iw-project-support",
    licenses = Seq(License.MIT),
    versionControl =
      VersionControl.github("iterative-works", "iw-project-support"),
    developers = Seq(
      Developer(
        "mprihoda",
        "Michal Příhoda",
        "https://github.com/iterative-works"
      )
    )
  )

  def ivyDeps = Agg(
    ivy"com.lihaoyi::os-lib:0.9.3"
  )

  // Mill's own dependencies needed for compilation
  def compileIvyDeps = Agg(
    ivy"com.lihaoyi::mill-main:${millVersion}",
    ivy"com.lihaoyi::mill-scalalib:${millVersion}"
  )

  object test extends ScalaTests with Utest with ScalafmtModule {
    def ivyDeps = Agg(
      ivy"com.lihaoyi::utest:0.8.2"
    )
  }
}
