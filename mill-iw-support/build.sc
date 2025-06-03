import mill._
import mill.scalalib._
import mill.scalalib.publish._
import mill.scalalib.scalafmt._
import mill.scalalib.TestModule.Utest
import mill.util.BuildInfo.{millVersion, millBinPlatform}

/** Main module for the mill-iw-support library. Built for Scala 2.13 which is
  * compatible with Mill.
  */
object `mill-iw-support` extends ScalaModule with ScalafmtModule with PublishModule {
  def scalaVersion = "3.7.0"

  def platformSuffix = s"_mill$millBinPlatform"

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

  def mvnDeps = Seq(
    mvn"com.lihaoyi::os-lib:0.9.3",
    mvn"com.lihaoyi::mill-libs:${millVersion}"
  )

  object test extends ScalaTests with Utest with ScalafmtModule {
    def mvnDeps = Seq(mvn"com.lihaoyi::mill-testkit:$millVersion")
        def forkEnv = Task {
        val artifact = s"${`mill-iw-support`.pomSettings().organization}-${`mill-iw-support`.artifactId()}"
            .replaceAll("[.-]", "_")
            .toUpperCase

        val localClasspathString = `mill-iw-support`.localClasspath().map(_.path).mkString("\n")
        Map(
            "MILL_EXECUTABLE_PATH" -> millExecutable.assembly().path.toString,
            s"MILL_LOCAL_TEST_OVERRIDE_$artifact" -> localClasspathString
        )
        }

        // Create a Mill executable configured for testing our plugin
        object millExecutable extends JavaModule {
        def mvnDeps = Seq(mvn"com.lihaoyi:mill-runner-launcher_3:$millVersion")
        def mainClass = Some("mill.launcher.MillLauncherMain")
    }
  }
}
