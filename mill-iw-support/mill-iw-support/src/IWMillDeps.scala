package works.iterative.mill

import mill._
import mill.scalalib._

/** Standardized dependency management for Iterative Works projects using Mill. This object provides
  * ready-to-use Maven dependencies with proper versioning.
  *
  * All versions are centralized in [[IWMillVersions]].
  *
  * This object is designed to be used in build.sc files:
  *
  * {{{
  * def mvnDeps = super.mvnDeps() ++ Agg(
  *   IWMillDeps.zio,
  *   IWMillDeps.zioJson
  * )
  * }}}
  */
object IWMillDeps {
    val V = IWMillVersions

    // ZIO Core
    val zio = mvn"dev.zio::zio::${V.zio}"
    val zioTest = mvn"dev.zio::zio-test::${V.zio}"
    val zioTestSbt = mvn"dev.zio::zio-test-sbt::${V.zio}"
    val zioStreams = mvn"dev.zio::zio-streams::${V.zio}"

    // ZIO Extensions
    val zioConfig = mvn"dev.zio::zio-config::${V.zioConfig}"
    val zioConfigTypesafe = mvn"dev.zio::zio-config-typesafe:${V.zioConfig}"
    val zioConfigMagnolia = mvn"dev.zio::zio-config-magnolia::${V.zioConfig}"
    val zioJson = mvn"dev.zio::zio-json::${V.zioJson}"
    val zioLogging = mvn"dev.zio::zio-logging::${V.zioLogging}"
    val zioLoggingSlf4j = mvn"dev.zio::zio-logging-slf4j:${V.zioLogging}"
    val zioPrelude = mvn"dev.zio::zio-prelude::${V.zioPrelude}"
    val zioInteropCats = mvn"dev.zio::zio-interop-cats::${V.zioInteropCats}"
    val zioInteropReactiveStreams =
        mvn"dev.zio::zio-interop-reactivestreams::${V.zioInteropReactiveStreams}"
    val zioNIO = mvn"dev.zio::zio-nio:${V.zioNIO}"
    val zioCli = mvn"dev.zio::zio-cli:${V.zioCli}"
    val zioSchema = mvn"dev.zio::zio-schema::${V.zioSchema}"
    val zioSchemaDerivation = mvn"dev.zio::zio-schema-derivation::${V.zioSchema}"

    // ScalaJS dependencies
    val scalaJsDom = mvn"org.scala-js::scalajs-dom::2.8.0"

    // Database
    val quill = mvn"io.getquill::quill-jdbc-zio:${V.quill}"
    val magnum = mvn"com.augustnagro::magnum_3:${V.magnum}"
    val magnumZIO = mvn"com.augustnagro::magnumzio_3:${V.magnum}"
    val magnumPG = mvn"com.augustnagro::magnumpg_3:${V.magnum}"

    // HTTP
    val tapirCore = mvn"com.softwaremill.sttp.tapir::tapir-core::${V.tapir}"
    val tapirZIO = mvn"com.softwaremill.sttp.tapir::tapir-zio::${V.tapir}"
    val tapirZIOJson = mvn"com.softwaremill.sttp.tapir::tapir-json-zio::${V.tapir}"
    val tapirSttpClient = mvn"com.softwaremill.sttp.tapir::tapir-sttp-client::${V.tapir}"
    val tapirZIOHttp4sServer = mvn"com.softwaremill.sttp.tapir::tapir-http4s-server-zio::${V.tapir}"
    
    // STTP Client
    val sttpClientZio = mvn"com.softwaremill.sttp.client3::zio::${V.sttpClient3}"

    // UI/Frontend
    val scalatags = mvn"com.lihaoyi::scalatags::${V.scalatags}"
    val laminar = mvn"com.raquo::laminar::${V.laminar}"
    val waypoint = mvn"com.raquo::waypoint::${V.waypoint}"
    val urlDsl = mvn"be.doeraene::url-dsl::${V.urlDsl}"

    // Testing
    val scalaTest = mvn"org.scalatest::scalatest::${V.scalaTest}"
    val utest = mvn"com.lihaoyi::utest::${V.utest}"

    // Logging
    val logbackClassic = mvn"ch.qos.logback:logback-classic:${V.logbackClassic}"
    
    // Utility
    // Note: silencer-lib is only available for Scala 2.13
    val silencerLib = mvn"com.github.ghik::silencer-lib::1.4.2"
    
    // MongoDB
    // Note: mongo-scala-driver is only available for Scala 2.13
    val mongoScalaDriver = mvn"org.mongodb.scala::mongo-scala-driver::4.2.3"
}
