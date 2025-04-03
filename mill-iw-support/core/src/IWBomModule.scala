package works.iterative.mill

import mill._
import mill.scalalib._
import mill.scalalib.publish._

/** Standard Bill of Materials (BOM) module for Iterative Works projects using
  * Mill. This module centralizes dependency versioning across projects.
  *
  * A BOM module allows:
  *   1. Centralized version management for dependencies 2. Enforcing specific
  *      versions of transitive dependencies 3. Applying uniform dependency
  *      management across multiple modules 4. Publishing a BOM artifact that
  *      other projects can consume
  *
  * Usage:
  *   1. Create a BOM module in your build:
  *      ```scala
  *      object bom extends IWBomModule {
  *        def publishVersion = "0.1.0-SNAPSHOT"
  *
  *        // Add your organization-specific customizations here
  *        def pomSettings = PomSettings(...)
  *      }
  *      ```
  *
  * 2. Use the BOM in your modules:
  * ```scala
  * object myModule extends IWScalaModule {
  *   def bomModuleDeps = Seq(bom)
  *
  *   def ivyDeps = Agg(
  *     // No need to specify versions as they come from the BOM
  *     ivy"dev.zio::zio",
  *     ivy"dev.zio::zio-json"
  *   )
  * }
  * ```
  */
trait IWBomModule extends BomModule with ScalaModule with IWScalaVersions {

  override def scalaVersion: T[String] = scala3Version

  /** The default implementation includes all standard IW dependencies from
    * IWMillVersions and IWMillDeps.
    */
  override def depManagement = T {
    val V = IWMillVersions

    Agg(
      // ZIO Core
      ivy"dev.zio::zio:${V.zio}",
      ivy"dev.zio::zio-test:${V.zio}",
      ivy"dev.zio::zio-test-sbt:${V.zio}",
      ivy"dev.zio::zio-streams:${V.zio}",

      // ZIO Extensions
      ivy"dev.zio::zio-config:${V.zioConfig}",
      ivy"dev.zio::zio-config-typesafe:${V.zioConfig}",
      ivy"dev.zio::zio-config-magnolia:${V.zioConfig}",
      ivy"dev.zio::zio-json:${V.zioJson}",
      ivy"dev.zio::zio-logging:${V.zioLogging}",
      ivy"dev.zio::zio-logging-slf4j:${V.zioLogging}",
      ivy"dev.zio::zio-prelude:${V.zioPrelude}",
      ivy"dev.zio::zio-interop-cats:${V.zioInteropCats}",
      ivy"dev.zio::zio-interop-reactivestreams:${V.zioInteropReactiveStreams}",
      ivy"dev.zio::zio-nio:${V.zioNIO}",
      ivy"dev.zio::zio-cli:${V.zioCli}",
      ivy"dev.zio::zio-schema:${V.zioSchema}",
      ivy"dev.zio::zio-schema-derivation:${V.zioSchema}",

      // Database
      ivy"io.getquill::quill-jdbc-zio:${V.quill}",
      ivy"com.augustnagro::magnum:${V.magnum}",
      ivy"com.augustnagro::magnumzio:${V.magnum}",
      ivy"com.augustnagro::magnumpg:${V.magnum}",

      // HTTP
      ivy"com.softwaremill.sttp.tapir::tapir-core:${V.tapir}",
      ivy"com.softwaremill.sttp.tapir::tapir-zio:${V.tapir}",
      ivy"com.softwaremill.sttp.tapir::tapir-json-zio:${V.tapir}",

      // UI/Frontend
      ivy"com.lihaoyi::scalatags:${V.scalatags}",
      ivy"com.raquo::laminar:${V.laminar}",
      ivy"com.raquo::waypoint:${V.waypoint}",
      ivy"be.doeraene::url-dsl:${V.urlDsl}",

      // Testing
      ivy"org.scalatest::scalatest:${V.scalaTest}",
      ivy"com.lihaoyi::utest:${V.utest}",

      // Logging
      ivy"ch.qos.logback:logback-classic:${V.logbackClassic}"
    )
  }
}
