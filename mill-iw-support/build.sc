import mill._
import mill.scalalib._
import mill.scalalib.publish._
import mill.scalalib.scalafmt._

object `mill-iw-support` extends Cross[IWMillSupportModule]("2.13.16", "3.6.3")

class IWMillSupportModule(val crossScalaVersion: String) extends CrossScalaModule with ScalafmtModule with PublishModule {
  def publishVersion = "0.1.0-SNAPSHOT"

  def pomSettings = PomSettings(
    description = "Iterative Works Mill Support Library",
    organization = "works.iterative",
    url = "https://github.com/iterative-works/mill-iw-support",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("iterative-works", "mill-iw-support"),
    developers = Seq(
      Developer("mph", "Michal Přihoda", "https://github.com/iterative-works")
    )
  )

  def ivyDeps = Agg(
    ivy"com.lihaoyi::os-lib:0.9.3"
  )

  object test extends Tests with ScalafmtModule {
    def ivyDeps = Agg(
      ivy"com.lihaoyi::utest:0.8.2"
    )
    def testFramework = "utest.runner.Framework"
  }
}