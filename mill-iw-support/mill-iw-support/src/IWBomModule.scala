package works.iterative.mill

import mill._
import mill.scalalib._
import mill.scalalib.publish._

/** Standard Bill of Materials (BOM) module for Iterative Works projects using Mill. This module
  * centralizes dependency versioning across projects.
  *
  * A BOM module allows:
  *   1. Centralized version management for dependencies 2. Enforcing specific versions of
  *      transitive dependencies 3. Applying uniform dependency management across multiple modules
  *      4. Publishing a BOM artifact that other projects can consume
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
  *     def bomModuleDeps = Seq(bom)
  *
  *     def mvnDeps = Agg(
  *         // No need to specify versions as they come from the BOM
  *         mvn"dev.zio::zio",
  *         mvn"dev.zio::zio-json"
  *     )
  * }
  * ```
  */
trait IWBomModule extends BomModule with PublishModule with ScalaModule with IWScalaVersions {

    override def scalaVersion: T[String] = scala3Version

    /** The default implementation includes all standard IW dependencies from IWMillVersions and
      * IWMillDeps.
      */
    override def depManagement = Task {
        val V = IWMillVersions

        Seq(
            // ZIO Core
            mvn"dev.zio::zio::${V.zio}",
            mvn"dev.zio::zio-test::${V.zio}",
            mvn"dev.zio::zio-test-sbt::${V.zio}",
            mvn"dev.zio::zio-streams::${V.zio}",

            // ZIO Extensions
            mvn"dev.zio::zio-config::${V.zioConfig}",
            mvn"dev.zio::zio-config-typesafe::${V.zioConfig}",
            mvn"dev.zio::zio-config-magnolia::${V.zioConfig}",
            mvn"dev.zio::zio-json::${V.zioJson}",
            mvn"dev.zio::zio-logging::${V.zioLogging}",
            mvn"dev.zio::zio-logging-slf4j:${V.zioLogging}",
            mvn"dev.zio::zio-prelude::${V.zioPrelude}",
            mvn"dev.zio::zio-interop-cats::${V.zioInteropCats}",
            mvn"dev.zio::zio-interop-reactivestreams::${V.zioInteropReactiveStreams}",
            mvn"dev.zio::zio-nio:${V.zioNIO}",
            mvn"dev.zio::zio-cli::${V.zioCli}",
            mvn"dev.zio::zio-schema::${V.zioSchema}",
            mvn"dev.zio::zio-schema-derivation::${V.zioSchema}",

            // Database
            mvn"io.getquill::quill-jdbc-zio:${V.quill}",
            mvn"com.augustnagro::magnum:${V.magnum}",
            mvn"com.augustnagro::magnumzio:${V.magnum}",
            mvn"com.augustnagro::magnumpg:${V.magnum}",

            // HTTP
            mvn"com.softwaremill.sttp.tapir::tapir-core::${V.tapir}",
            mvn"com.softwaremill.sttp.tapir::tapir-zio::${V.tapir}",
            mvn"com.softwaremill.sttp.tapir::tapir-json-zio::${V.tapir}",

            // UI/Frontend
            mvn"com.lihaoyi::scalatags::${V.scalatags}",
            mvn"com.raquo::laminar::${V.laminar}",
            mvn"com.raquo::waypoint::${V.waypoint}",
            mvn"be.doeraene::url-dsl::${V.urlDsl}",

            // Testing
            mvn"org.scalatest::scalatest::${V.scalaTest}",
            mvn"com.lihaoyi::utest::${V.utest}",

            // Logging
            mvn"ch.qos.logback:logback-classic:${V.logbackClassic}"
        )
    }
}
