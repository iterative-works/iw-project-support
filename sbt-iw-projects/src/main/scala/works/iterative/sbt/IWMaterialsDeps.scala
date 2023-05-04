package works.iterative.sbt

import io.github.sbt.tzdb.TzdbPlugin
import io.github.sbt.tzdb.TzdbPlugin.autoImport.zonesFilter
import locales.LocalesPlugin
import locales.LocalesPlugin.autoImport.{currencyFilter, localesFilter, supportISOCodes, supportNumberFormats}
import sbt._
import sbt.Keys.{libraryDependencies, testFrameworks}
import sbt.librarymanagement.CrossVersion
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._

object IWMaterialsDeps extends AkkaLibs with SlickLibs with IWSupport {
  import works.iterative.sbt.IWMaterialsVersions as V

  val zioOrg = "dev.zio"

  def zioLib(
      name: String,
      version: String,
      configurations: Option[String]
  ): Def.Setting[_] =
    libraryDependencies += (zioOrg %%% s"zio-$name" % version)
      .withConfigurations(configurations)

  def zioLib(
      name: String,
      version: String,
      config: Configuration*
  ): Def.Setting[_] = zioLib(
    name,
    version,
    if (config.isEmpty) None else Some(config.map(_.name).mkString(","))
  )

  lazy val zio: Def.Setting[_] = libraryDependencies += zioOrg %%% "zio" % V.zio

  def zioTest(c: Configuration*): Def.Setting[_] =
    zioLib("test", V.zio, c: _*)

  def zioTestSbt(c: Configuration*): Def.Setting[_] =
    zioLib("test-sbt", V.zio, c: _*)

  lazy val zioConfig: Def.Setting[_] = zioLib("config", V.zioConfig)
  lazy val zioConfigTypesafe: Def.Setting[_] =
    zioLib("config-typesafe", V.zioConfig)
  lazy val zioConfigMagnolia: Def.Setting[_] =
    zioLib("config-magnolia", V.zioConfig)

  lazy val zioJson: Def.Setting[_] = zioLib("json", V.zioJson)
  lazy val zioLogging: Def.Setting[_] = zioLib("logging", V.zioLogging)
  lazy val zioLoggingSlf4j: Def.Setting[_] =
    zioLib("logging-slf4j", V.zioLogging)
  lazy val zioPrelude: Def.Setting[_] = zioLib("prelude", V.zioPrelude)
  lazy val zioStreams: Def.Setting[_] = zioLib("streams", V.zio)
  lazy val zioInteropCats: Def.Setting[_] =
    zioLib("interop-cats", V.zioInteropCats)
  lazy val zioInteropReactiveStreams: Def.Setting[_] =
    zioLib("interop-reactivestreams", V.zioInteropReactiveStreams)
  lazy val zioNIO: Def.Setting[_] = zioLib("nio", V.zioNIO)
  lazy val zioOptics: Def.Setting[_] = zioLib("optics", V.zioOptics)
  lazy val zioQuery: Def.Setting[_] = zioLib("query", V.zioQuery)

  lazy val quill: Def.Setting[_] =
    libraryDependencies += "io.getquill" %% "quill-caliban" % V.quill

  lazy val zioSchema: Def.Setting[_] = zioLib("schema", V.zioSchema)

  lazy val zioSchemaDerivation: Def.Setting[_] = zioLib("schema-derivation", V.zioSchema)

  lazy val caliban: Def.Setting[_] =
  libraryDependencies += "com.github.ghostdogpr" %% "caliban" % "2.1.0"

  lazy val calibanHttp4s: Def.Setting[_] =
    libraryDependencies += "com.github.ghostdogpr" %% "caliban-http4s" % V.caliban

  lazy val calibanTapir: Def.Setting[_] =
    libraryDependencies += "com.github.ghostdogpr" %% "caliban-tapir" % "2.1.0"

  def useZIOTest(testConf: Configuration*): Seq[Def.Setting[_]] = {
    val testConfigurations = if (testConf.isEmpty) Seq(Test) else testConf
    Seq(
      zioTest(testConfigurations: _*),
      zioTestSbt(testConfigurations: _*),
      testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
    )
  }

  def useZIOJson: Seq[Def.Setting[_]] = {
    import _root_.io.github.davidgregory084.TpolecatPlugin.autoImport.*

    val retainTrees = {
      import _root_.io.github.davidgregory084.ScalaVersion.*

      import scala.Ordering.Implicits.*
      ScalacOptions.privateOption(
        "retain-trees",
        version => {
          version >= V3_0_0
        }
      )
    }

    Seq(
      zioJson,
      tpolecatScalacOptions += retainTrees
    )
  }

  def useZIO(testConf: Configuration*): Seq[Def.Setting[_]] =
    zio +: useZIOTest(testConf: _*)

  def useZIOAll(testConf: Configuration*): Seq[Def.Setting[_]] =
    useZIO(testConf: _*) ++ Seq(
      zioStreams,
      zioConfig,
      zioConfigTypesafe,
      zioConfigMagnolia,
      zioLogging,
      zioPrelude
    ) ++ useZIOJson

  private val tapirOrg = "com.softwaremill.sttp.tapir"
  def tapirLib(name: String): Def.Setting[_] =
    libraryDependencies += tapirOrg %%% s"tapir-$name" % V.tapir

  lazy val tapirCore: Def.Setting[_] = tapirLib("core")
  lazy val tapirZIO: Def.Setting[_] = tapirLib("zio")
  lazy val tapirZIOJson: Def.Setting[_] = tapirLib("json-zio")
  lazy val tapirSttpClient: Def.Setting[_] = tapirLib("sttp-client")
  lazy val tapirCats: Def.Setting[_] = tapirLib("cats")
  lazy val tapirZIOHttp4sServer: Def.Setting[_] = tapirLib("http4s-server-zio")

  private val sttpClientOrg = "com.softwaremill.sttp.client3"
  def sttpClientLib(name: String): Def.Setting[_] =
    libraryDependencies += sttpClientOrg %%% name % V.sttpClient

  lazy val sttpClientCore: Def.Setting[_] = sttpClientLib("core")

  lazy val http4sBlazeServer: Def.Setting[_] =
    libraryDependencies += "org.http4s" %% "http4s-blaze-server" % V.http4sBlaze

  lazy val http4sPac4J: Def.Setting[_] =
    libraryDependencies += "org.pac4j" %% "http4s-pac4j" % V.http4sPac4J
  lazy val pac4jOIDC: Def.Setting[_] =
    libraryDependencies += "org.pac4j" % "pac4j-oidc" % V.pac4j

  lazy val scalaTest: Def.Setting[_] =
    libraryDependencies += "org.scalatest" %%% "scalatest" % V.scalaTest
  lazy val scalaTestPlusScalacheck: Def.Setting[_] =
    libraryDependencies += "org.scalatestplus" %% "scalacheck-1-15" % "3.2.14.0"
  lazy val playScalaTest: Def.Setting[_] =
    libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0"

  private val playOrg = "com.typesafe.play"
  lazy val playMailer: Def.Setting[_] =
    libraryDependencies += playOrg %% "play-mailer" % "8.0.1"
  lazy val playServer: Def.Setting[_] =
    libraryDependencies += playOrg %% "play-server" % V.play
  lazy val playAkkaServer: Def.Setting[_] =
    libraryDependencies += playOrg %% "play-akka-http-server" % V.play
  lazy val play: Def.Setting[_] =
    libraryDependencies += playOrg %% "play" % V.play
  lazy val playAhcWs: Def.Setting[_] =
    libraryDependencies += playOrg %% "play-ahc-ws" % V.play
  lazy val playJson: Def.Setting[_] =
    libraryDependencies += playOrg %%% "play-json" % V.playJson

  private val elastic4sOrg = "com.sksamuel.elastic4s"
  lazy val useElastic4S: Def.Setting[_] = libraryDependencies ++= Seq(
    elastic4sOrg %% "elastic4s-core" % V.elastic4s,
    elastic4sOrg %% "elastic4s-client-akka" % V.elastic4s,
    elastic4sOrg %% "elastic4s-http-streams" % V.elastic4s,
    elastic4sOrg %% "elastic4s-json-play" % V.elastic4s
  )

  lazy val laminar: Def.Setting[_] =
    libraryDependencies += "com.raquo" %%% "laminar" % V.laminar

  private def laminext(name: String): Def.Setting[_] =
    libraryDependencies += "io.laminext" %%% name % V.laminext

  lazy val laminextCore: Def.Setting[_] = laminext("core")
  lazy val laminextTailwind: Def.Setting[_] = laminext("tailwind")
  lazy val laminextFetch: Def.Setting[_] = laminext("fetch")
  lazy val laminextValidationCore: Def.Setting[_] = laminext("validation-core")
  lazy val laminextUI: Def.Setting[_] = laminext("ui")

  lazy val waypoint: Def.Setting[_] =
    libraryDependencies += "com.raquo" %%% "waypoint" % V.waypoint

  lazy val urlDsl: Def.Setting[_] =
    libraryDependencies += "be.doeraene" %%% "url-dsl" % V.urlDsl

  lazy val addScalaJavaTime: Def.Setting[_] =
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.5.0"

  lazy val addScalaJavaLocales: Def.Setting[_] =
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-locales" % "1.5.1"

  def useScalaJavaTimeAndLocales(proj: Project): Project =
    proj
      .enablePlugins(TzdbPlugin, LocalesPlugin)
      .settings(
        addScalaJavaTime,
        addScalaJavaLocales,
        localesFilter := locales.LocalesFilter
          .Selection("en-US", "en", "cs-CZ", "cs"),
        currencyFilter := locales.CurrencyFilter.Selection(
          "AUD",
          "BGN",
          "CAD",
          "CNY",
          "CZK",
          "DKK",
          "EUR",
          "GBP",
          "HKD",
          "HRK",
          "HUF",
          "CHF",
          "ILS",
          "JPY",
          "KRW",
          "NOK",
          "NZD",
          "PLN",
          "RON",
          "RUB",
          "SEK",
          "TRY",
          "USD"
        ),
        supportISOCodes := false,
        supportNumberFormats := true,
        zonesFilter := { (z: String) =>
          z == "Europe/Prague" || z == "CET" || z == "Etc/UTC" || z == "UTC"
        }
      )

  lazy val scalaJsMacroTaskExector: Def.Setting[_] =
    libraryDependencies += "org.scala-js" %%% "scala-js-macrotask-executor" % "1.1.1"

  lazy val scalaJsJavaSecureRandom: Def.Setting[_] =
    libraryDependencies += ("org.scala-js" %%% "scalajs-java-securerandom" % "1.0.0").cross(CrossVersion.for3Use2_13)

  lazy val logbackClassic: Def.Setting[_] =
    libraryDependencies += "ch.qos.logback" % "logback-classic" % V.logbackClassic % Runtime
}
