package works.iterative.mill

import mill.api.Loose
import mill.scalalib.Dep

/**
 * Standardized dependency management for Iterative Works projects using Mill.
 * This object provides methods to get dependencies with proper versioning.
 * 
 * All versions are centralized in [[IWMillVersions]].
 */
object IWMillDeps {
  import IWMillVersions as V

  // ZIO Core
  def zio = ivy"dev.zio::zio:${V.zio}"
  def zioTest = ivy"dev.zio::zio-test:${V.zio}"
  def zioTestSbt = ivy"dev.zio::zio-test-sbt:${V.zio}"
  def zioStreams = ivy"dev.zio::zio-streams:${V.zio}"

  // ZIO Extensions
  def zioConfig = ivy"dev.zio::zio-config:${V.zioConfig}"
  def zioConfigTypesafe = ivy"dev.zio::zio-config-typesafe:${V.zioConfig}"
  def zioConfigMagnolia = ivy"dev.zio::zio-config-magnolia:${V.zioConfig}"
  def zioJson = ivy"dev.zio::zio-json:${V.zioJson}"
  def zioLogging = ivy"dev.zio::zio-logging:${V.zioLogging}"
  def zioLoggingSlf4j = ivy"dev.zio::zio-logging-slf4j:${V.zioLogging}"
  def zioPrelude = ivy"dev.zio::zio-prelude:${V.zioPrelude}"
  def zioInteropCats = ivy"dev.zio::zio-interop-cats:${V.zioInteropCats}"
  def zioInteropReactiveStreams = ivy"dev.zio::zio-interop-reactivestreams:${V.zioInteropReactiveStreams}"
  def zioNIO = ivy"dev.zio::zio-nio:${V.zioNIO}"
  def zioCli = ivy"dev.zio::zio-cli:${V.zioCli}"
  def zioSchema = ivy"dev.zio::zio-schema:${V.zioSchema}"
  def zioSchemaDerivation = ivy"dev.zio::zio-schema-derivation:${V.zioSchema}"

  // Helper methods for ZIO
  def useZIO(): Loose.Agg[Dep] = Loose.Agg(
    zio,
    zioTest,
    zioTestSbt
  )

  def useZIOJson: Loose.Agg[Dep] = Loose.Agg(
    zioJson
  )

  def useZIOAll(): Loose.Agg[Dep] = useZIO() ++ Loose.Agg(
    zioStreams,
    zioConfig,
    zioConfigTypesafe,
    zioConfigMagnolia,
    zioLogging,
    zioPrelude
  ) ++ useZIOJson

  // Database
  def quill = ivy"io.getquill::quill-jdbc-zio:${V.quill}"
  def magnum = ivy"com.augustnagro::magnum_3:${V.magnum}"
  def magnumZIO = ivy"com.augustnagro::magnumzio_3:${V.magnum}"
  def magnumPG = ivy"com.augustnagro::magnumpg_3:${V.magnum}"

  // HTTP
  def tapirCore = ivy"com.softwaremill.sttp.tapir::tapir-core:${V.tapir}"
  def tapirZIO = ivy"com.softwaremill.sttp.tapir::tapir-zio:${V.tapir}"
  def tapirZIOJson = ivy"com.softwaremill.sttp.tapir::tapir-json-zio:${V.tapir}"
  
  // UI/Frontend
  def scalatags = ivy"com.lihaoyi::scalatags:${V.scalatags}"
  def laminar = ivy"com.raquo::laminar:${V.laminar}"
  def waypoint = ivy"com.raquo::waypoint:${V.waypoint}"
  def urlDsl = ivy"be.doeraene::url-dsl:${V.urlDsl}"
  
  // Testing
  def scalaTest = ivy"org.scalatest::scalatest:${V.scalaTest}"
  
  // Logging
  def logbackClassic = ivy"ch.qos.logback:logback-classic:${V.logbackClassic}"
}