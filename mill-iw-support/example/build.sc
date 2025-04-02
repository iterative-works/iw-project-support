import mill._
import mill.scalalib._
import $ivy.`works.iterative::mill-iw-support:0.1.0-SNAPSHOT`
import works.iterative.mill._

object example extends IWScalaModule with IWPublishModule {
  def publishVersion = "0.1.0-SNAPSHOT"
  
  def pomSettings = PomSettings(
    description = "Example Mill Project using IW Mill Support",
    organization = "com.example",
    url = "https://github.com/example/example",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("example", "example"),
    developers = Seq(
      Developer("developer", "Example Developer", "https://github.com/example")
    )
  )
  
  // Use centralized dependency versions and definitions
  def ivyDeps = super.ivyDeps() ++ IWMillDeps.useZIO() ++ Agg(
    IWMillDeps.zioJson,
    IWMillDeps.tapirCore,
    IWMillDeps.tapirZIO,
    IWMillDeps.tapirZIOJson
  )
  
  // Define test module extending IWTests
  object test extends Tests with IWTests
}