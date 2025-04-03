package works.iterative.mill

import mill.api.Loose

/** Standardized dependency management for Iterative Works projects using Mill.
  * This object provides methods to get dependency strings with proper
  * versioning.
  *
  * All versions are centralized in [[IWMillVersions]].
  *
  * This object is designed to be used in build.sc files where the ivy string
  * interpolator is available, like:
  *
  * {{{
  * def ivyDeps = Agg(
  *   ivy"$${IWMillDeps.zio}",
  *   ivy"$${IWMillDeps.zioJson}"
  * )
  * }}}
  */
object IWMillDeps {
  val V = IWMillVersions

  // ZIO Core
  val zio = s"dev.zio::zio:${V.zio}"
  val zioTest = s"dev.zio::zio-test:${V.zio}"
  val zioTestSbt = s"dev.zio::zio-test-sbt:${V.zio}"
  val zioStreams = s"dev.zio::zio-streams:${V.zio}"

  // ZIO Extensions
  val zioConfig = s"dev.zio::zio-config:${V.zioConfig}"
  val zioConfigTypesafe = s"dev.zio::zio-config-typesafe:${V.zioConfig}"
  val zioConfigMagnolia = s"dev.zio::zio-config-magnolia:${V.zioConfig}"
  val zioJson = s"dev.zio::zio-json:${V.zioJson}"
  val zioLogging = s"dev.zio::zio-logging:${V.zioLogging}"
  val zioLoggingSlf4j = s"dev.zio::zio-logging-slf4j:${V.zioLogging}"
  val zioPrelude = s"dev.zio::zio-prelude:${V.zioPrelude}"
  val zioInteropCats = s"dev.zio::zio-interop-cats:${V.zioInteropCats}"
  val zioInteropReactiveStreams =
    s"dev.zio::zio-interop-reactivestreams:${V.zioInteropReactiveStreams}"
  val zioNIO = s"dev.zio::zio-nio:${V.zioNIO}"
  val zioCli = s"dev.zio::zio-cli:${V.zioCli}"
  val zioSchema = s"dev.zio::zio-schema:${V.zioSchema}"
  val zioSchemaDerivation = s"dev.zio::zio-schema-derivation:${V.zioSchema}"

  // Collection of ZIO dependencies
  val zioCore = List(zio, zioTest, zioTestSbt)
  val zioJson_ = List(zioJson)
  val zioAll = zioCore ++ List(
    zioStreams,
    zioConfig,
    zioConfigTypesafe,
    zioConfigMagnolia,
    zioLogging,
    zioPrelude
  ) ++ zioJson_

  // Database
  val quill = s"io.getquill::quill-jdbc-zio:${V.quill}"
  val magnum = s"com.augustnagro::magnum_3:${V.magnum}"
  val magnumZIO = s"com.augustnagro::magnumzio_3:${V.magnum}"
  val magnumPG = s"com.augustnagro::magnumpg_3:${V.magnum}"

  // HTTP
  val tapirCore = s"com.softwaremill.sttp.tapir::tapir-core:${V.tapir}"
  val tapirZIO = s"com.softwaremill.sttp.tapir::tapir-zio:${V.tapir}"
  val tapirZIOJson = s"com.softwaremill.sttp.tapir::tapir-json-zio:${V.tapir}"

  // UI/Frontend
  val scalatags = s"com.lihaoyi::scalatags:${V.scalatags}"
  val laminar = s"com.raquo::laminar:${V.laminar}"
  val waypoint = s"com.raquo::waypoint:${V.waypoint}"
  val urlDsl = s"be.doeraene::url-dsl:${V.urlDsl}"

  // Testing
  val scalaTest = s"org.scalatest::scalatest:${V.scalaTest}"

  // Logging
  val logbackClassic = s"ch.qos.logback:logback-classic:${V.logbackClassic}"
}
