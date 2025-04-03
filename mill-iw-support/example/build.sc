import mill._
import mill.scalalib._
import mill.scalalib.publish._
import mill.scalalib.scalafmt._

// Import our IW Mill Support library
import $ivy.`works.iterative::mill-iw-support:0.1.0-SNAPSHOT`
import works.iterative.mill._

// Define a BOM (Bill of Materials) module
object bom extends IWBomModule

// Example module using the IW Mill support with BOM
object example extends IWScalaModule with IWPublishModule {
  def publishVersion = "0.1.0-SNAPSHOT"

  // Use the BOM module for dependency management
  override def bomModuleDeps = Seq(bom)

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

  // With BOM, we don't need to specify versions
  // They're managed by the BOM module
  def ivyDeps = super.ivyDeps() ++ Agg(
    ivy"dev.zio::zio",
    ivy"dev.zio::zio-json",
    ivy"com.softwaremill.sttp.tapir::tapir-core",
    ivy"com.softwaremill.sttp.tapir::tapir-zio",
    ivy"com.softwaremill.sttp.tapir::tapir-json-zio"
  )

  // Define test module using standard Mill approach
  object test extends ScalaTests with TestModule.ZioTest {
    // Use the BOM module for dependency management
    override def bomModuleDeps = Seq(bom)

    def ivyDeps = super.ivyDeps() ++ Agg(
      ivy"dev.zio::zio-test", // Version comes from BOM
      ivy"dev.zio::zio-test-sbt" // Version comes from BOM
    )
  }
}
