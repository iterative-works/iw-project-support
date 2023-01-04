package works.iterative.sbt

import sbt._
import sbt.Keys._
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import _root_.io.github.sbt.tzdb.TzdbPlugin
import _root_.io.github.sbt.tzdb.TzdbPlugin.autoImport._
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
  val akka = "2.6.18"
  val akkaHttp = "10.4.0"
  val cats = "2.8.0"
  val elastic4s = "7.12.4"
  val http4s = "0.23.16"
  val http4sBlaze = "0.23.13"
  val http4sPac4J = "4.1.0"
  val laminar = "0.14.5"
  val laminext = "0.14.4"
  val logbackClassic = "1.4.4"
  val pac4j = "5.4.2"
  val play = "2.8.18"
  val playJson = "2.9.3"
  val scalaTest = "3.2.14"
  val slick = "3.3.3"
  val sttpClient = "3.8.6"
  val support = "0.1.3"
  val tapir = "1.2.4"
  val urlDsl = "0.4.3"
  val waypoint = "0.5.0"
  val zio = "2.0.5"
  val zioConfig = "3.0.6"
  val zioInteropCats = "3.3.0"
  val zioInteropReactiveStreams = "2.0.0"
  val zioJson = "0.4.2"
  val zioLogging = "2.1.7"
  val zioNIO = "2.0.0"
  val zioPrelude = "1.0.0-RC16"
}

object IWMaterialsDeps extends AkkaLibs with SlickLibs with IWSupport {
  import works.iterative.sbt.{IWMaterialsVersions => V}

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

  def useZIOTest(testConf: Configuration*): Seq[Def.Setting[_]] = {
    val testConfigurations = if (testConf.isEmpty) Seq(Test) else testConf
    Seq(
      zioTest(testConfigurations: _*),
      zioTestSbt(testConfigurations: _*),
      testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
    )
  }

  def useZIOJson: Seq[Def.Setting[_]] = {
    import _root_.io.github.davidgregory084.TpolecatPlugin.autoImport._

    val retainTrees = {
      import scala.Ordering.Implicits._
      import _root_.io.github.davidgregory084.ScalaVersion._
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
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.4.0"

  lazy val addScalaJavaLocales: Def.Setting[_] =
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-locales" % "1.4.1"

  def useScalaJavaTimeAndLocales(proj: Project): Project =
    proj
      .enablePlugins(TzdbPlugin, LocalesPlugin)
      .settings(
        addScalaJavaTime,
        addScalaJavaLocales,
        // Newer tzdata break the plugin because of format changes
        // Remove when fixed https://github.com/cquiroz/sbt-tzdb/issues/186
        dbVersion := TzdbPlugin.Version("2022a"),
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

  lazy val logbackClassic: Def.Setting[_] =
    libraryDependencies += "ch.qos.logback" % "logback-classic" % V.logbackClassic % Runtime
}

trait IWSupport {
  val supportOrg = "works.iterative.support"
  def supportName(name: String): String = s"iw-support-$name"

  class Support(version: String) {
    def supportMod(name: String): ModuleID =
      supportOrg %% supportName(name) % version
    def supportLib(name: String): Def.Setting[_] =
      libraryDependencies += supportMod(name)

    object modules {
      def core: ModuleID = supportMod("core")
      def codecs: ModuleID = supportMod("codecs")
      def tapir: ModuleID = supportMod("core")
      def mongo: ModuleID = supportMod("mongo")
      def `akka-persistence`: ModuleID = supportMod("akka-persistence")
      def ui: ModuleID = supportMod("ui")
      def `ui-model`: ModuleID = supportMod("ui-model")
    }

    object libs {
      def core: Def.Setting[_] =
        libraryDependencies += supportOrg %%% supportName("core") % version
      def codecs: Def.Setting[_] =
        libraryDependencies += supportOrg %%% supportName("codecs") % version
      def tapir: Def.Setting[_] =
        libraryDependencies += supportOrg %%% supportName("tapir") % version
      def mongo: Def.Setting[_] = supportLib("mongo")
      def `akka-persistence`: Def.Setting[_] = supportLib("akka-persistence")
      def ui: Def.Setting[_] =
        libraryDependencies += supportOrg %%% supportName("ui") % version
      def `ui-model`: Def.Setting[_] =
        libraryDependencies += supportOrg %%% supportName("ui-model") % version
      def `ui-components`: Def.Setting[_] =
        libraryDependencies += supportOrg %%% supportName(
          "ui-components"
        ) % version
    }
  }

  object support extends Support(IWMaterialsVersions.support)
}

trait AkkaLibs {

  self: SlickLibs =>

  object akka {
    val V = IWMaterialsVersions.akka
    val tOrg = "com.typesafe.akka"
    val lOrg = "com.lightbend.akka"

    def akkaMod(name: String): ModuleID =
      tOrg %% s"akka-$name" % V

    def akkaLib(name: String): Def.Setting[_] =
      libraryDependencies += akkaMod(name)

    object modules {
      lazy val actor: ModuleID = akkaMod("actor")
      lazy val actorTyped: ModuleID = akkaMod("actor-typed")
      lazy val stream: ModuleID = akkaMod("stream")
      lazy val persistence: ModuleID = akkaMod("persistence-typed")
      lazy val persistenceQuery: ModuleID = akkaMod("persistence-query")
      lazy val persistenceJdbc: ModuleID =
        lOrg %% "akka-persistence-jdbc" % "5.1.0"
      def persistenceTestKit(configs: Option[String] = None): ModuleID =
        (tOrg %% "akka-persistence-testkit" % V).withConfigurations(configs)
    }

    object libs {
      lazy val actor: Def.Setting[_] = akkaLib("actor")
      lazy val actorTyped: Def.Setting[_] = akkaLib("actor-typed")
      lazy val stream: Def.Setting[_] = akkaLib("stream")
      lazy val persistence: Def.Setting[_] = akkaLib("persistence-typed")
      lazy val persistenceQuery: Def.Setting[_] = akkaLib("persistence-query")
      lazy val persistenceJdbc: Def.Setting[_] =
        libraryDependencies += modules.persistenceJdbc

      def persistenceTestKit(configs: Option[String] = None): Def.Setting[_] =
        libraryDependencies += modules.persistenceTestKit(configs)
    }

    object http {
      val V = IWMaterialsVersions.akkaHttp

      object modules {
        lazy val http: ModuleID = tOrg %% "akka-http" % V
        lazy val sprayJson: ModuleID = tOrg %% "akka-http-spray-json" % V
      }

      object libs {
        lazy val http: Def.Setting[_] = libraryDependencies += modules.http
        lazy val sprayJson: Def.Setting[_] =
          libraryDependencies += modules.sprayJson
      }
    }

    object projection {
      val V = "1.2.5"

      object modules {
        lazy val core: ModuleID =
          lOrg %% "akka-projection-core" % V
        lazy val eventsourced: ModuleID =
          lOrg %% "akka-projection-eventsourced" % V
        lazy val slick: ModuleID =
          lOrg %% "akka-projection-slick" % V
        lazy val jdbc: ModuleID =
          lOrg %% "akka-projection-jdbc" % V
      }

      object libs {
        lazy val core: Def.Setting[_] =
          libraryDependencies += modules.core
        lazy val eventsourced: Def.Setting[_] =
          libraryDependencies += modules.eventsourced
        lazy val slick: Def.Setting[_] =
          libraryDependencies += modules.slick
        lazy val jdbc: Def.Setting[_] =
          libraryDependencies += modules.jdbc
      }
    }

    object profiles {
      // TODO: type safety for requirements
      lazy val eventsourcedJdbcProjection: Def.Setting[_] = {
        val allModules: Seq[ModuleID] = Seq(
          modules.persistenceQuery,
          projection.modules.core,
          projection.modules.eventsourced,
          projection.modules.slick,
          modules.persistenceJdbc
        ) ++ slick.modules.default
        libraryDependencies ++= {
          CrossVersion.partialVersion(scalaVersion.value) match {
            case Some((3, _)) =>
              allModules.map(
                _.cross(librarymanagement.CrossVersion.for3Use2_13)
                  .exclude(
                    "org.scala-lang.modules",
                    "scala-collection-compat_2.13"
                  )
              )
            case _ => allModules
          }
        }
      }
    }
  }

  @deprecated("use akka.libs.actor", "3.0.0")
  lazy val akkaActor: Def.Setting[_] = akka.libs.actor
  @deprecated("use akka.libs.actorTyped", "3.0.0")
  lazy val akkaActorTyped: Def.Setting[_] = akka.libs.actorTyped
  @deprecated("use akka.libs.stream", "3.0.0")
  lazy val akkaStream: Def.Setting[_] = akka.libs.stream
  @deprecated("use akka.libs.persistence", "3.0.0")
  lazy val akkaPersistence: Def.Setting[_] = akka.libs.persistence
  @deprecated("use akka.libs.persistenceQuery", "3.0.0")
  lazy val akkaPersistenceQuery: Def.Setting[_] = akka.libs.persistenceQuery
  @deprecated("use akka.libs.persistenceTestKit", "3.0.0")
  def akkaPersistenceTestKit(configs: Option[String] = None): Def.Setting[_] =
    akka.libs.persistenceTestKit(configs)

  @deprecated("use akka.http.libs.http", "3.0.0")
  lazy val akkaHttp: Def.Setting[_] = akka.http.libs.http
  @deprecated("use akka.http.libs.sprayJson", "3.0.0")
  lazy val akkaHttpSprayJson: Def.Setting[_] = akka.http.libs.sprayJson

}

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
