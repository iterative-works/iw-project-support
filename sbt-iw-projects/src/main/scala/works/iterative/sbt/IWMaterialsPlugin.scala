package works.iterative.sbt

import sbt._
import sbt.Keys._
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import _root_.io.gitub.sbt.tzdb.TzdbPlugin
import _root_.io.gitub.sbt.tzdb.TzdbPlugin.autoImport._
import locales.LocalesPlugin
import locales.LocalesPlugin.autoImport._

object IWMaterialsPlugin extends AutoPlugin {
  override def trigger: PluginTrigger = allRequirements
  object autoImport {
    val IWVersions = IWMaterialsVersions
    val IWDeps = IWMaterialsDeps
  }
}

object IWMaterialsVersions {
  val akka = "2.6.14"
  val akkaHttp = "10.2.4"
  val elastic4s = "7.12.2"
  val play = "2.8.8"
  val playJson = "2.9.2"
  val scalaTest = "3.2.9"
  val zio = "1.0.11"
  val zioConfig = "1.0.6"
  val zioJson = "0.1.5"
  val zioLogging = "0.5.11"
  val laminar = "0.13.1"
}

object IWMaterialsDeps {
  import works.iterative.sbt.{IWMaterialsVersions => V}

  val zio: Def.Setting[_] = libraryDependencies += "dev.zio" %%% "zio" % V.zio
  val zioTest: Def.Setting[_] =
    libraryDependencies += "dev.zio" %%% "zio-test" % V.zio % Test
  val zioTestSbt: Def.Setting[_] =
    libraryDependencies += "dev.zio" %%% "zio-test-sbt" % V.zio % Test

  val zioConfig: Def.Setting[_] =
    libraryDependencies += "dev.zio" %% "zio-config" % V.zioConfig
  val zioConfigTypesafe: Def.Setting[_] =
    libraryDependencies += "dev.zio" %% "zio-config-typesafe" % V.zioConfig

  val zioJson: Def.Setting[_] =
    libraryDependencies += "dev.zio" %%% "zio-json" % V.zioJson
  val zioLogging: Def.Setting[_] =
    libraryDependencies += "dev.zio" %%% "zio-logging" % V.zioLogging

  val useZIO: Seq[Def.Setting[_]] = Seq(
    zio,
    zioTest,
    zioTestSbt,
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )

  val zioStreams: Def.Setting[_] =
    libraryDependencies += "dev.zio" %%% "zio-streams" % V.zio

  private val akkaOrg = "com.typesafe.akka"
  val akkaActor: Def.Setting[_] =
    libraryDependencies += akkaOrg %% "akka-actor" % V.akka
  val akkaActorTyped: Def.Setting[_] =
    libraryDependencies += akkaOrg %% "akka-actor-typed" % V.akka
  val akkaHttp: Def.Setting[_] =
    libraryDependencies += akkaOrg %% "akka-http" % V.akkaHttp
  val akkaHttpSprayJson: Def.Setting[_] =
    libraryDependencies += akkaOrg %% "akka-http-spray-json" % V.akkaHttp
  val akkaStream: Def.Setting[_] =
    libraryDependencies += akkaOrg %% "akka-stream" % V.akka
  val akkaPersistenceTestKit: Def.Setting[_] =
    libraryDependencies += akkaOrg %% "akka-persistence-testkit" % V.akka

  val scalaTest: Def.Setting[_] =
    libraryDependencies += "org.scalatest" %%% "scalatest" % V.scalaTest % Test
  val scalaTestPlusScalacheck: Def.Setting[_] =
    libraryDependencies += "org.scalatestplus" %% "scalacheck-1-15" % "3.2.9.0" % Test
  val playScalaTest: Def.Setting[_] =
    libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test

  private val playOrg = "com.typesafe.play"
  val playMailer: Def.Setting[_] =
    libraryDependencies += playOrg %% "play-mailer" % "8.0.1"
  val playServer: Def.Setting[_] =
    libraryDependencies += playOrg %% "play-server" % V.play
  val playAkkaServer: Def.Setting[_] =
    libraryDependencies += playOrg %% "play-akka-http-server" % V.play
  val play: Def.Setting[_] = libraryDependencies += playOrg %% "play" % V.play
  val playAhcWs: Def.Setting[_] =
    libraryDependencies += playOrg %% "play-ahc-ws" % V.play
  val playJson: Def.Setting[_] =
    libraryDependencies += playOrg %%% "play-json" % V.playJson

  private val elastic4sOrg = "com.sksamuel.elastic4s"
  val useElastic4S: Def.Setting[_] = libraryDependencies ++= Seq(
    elastic4sOrg %% "elastic4s-core" % V.elastic4s,
    elastic4sOrg %% "elastic4s-client-akka" % V.elastic4s,
    elastic4sOrg %% "elastic4s-http-streams" % V.elastic4s,
    elastic4sOrg %% "elastic4s-json-play" % V.elastic4s
  )

  val laminar: Def.Setting[_] =
    libraryDependencies += "com.raquo" %%% "laminar" % V.laminar

  val laminextTailwind: Def.Setting[_] =
    libraryDependencies += "io.laminext" %%% "tailwind" % "0.13.10"

  val laminextFetch: Def.Setting[_] =
    libraryDependencies += "io.laminext" %%% "fetch" % "0.13.10"

  val addScalaJavaTime: Def.Setting[_] =
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.3.0"

  val addScalaJavaLocales: Def.Setting[_] =
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-locales" % "1.2.1"

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
}
