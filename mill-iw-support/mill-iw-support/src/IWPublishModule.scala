package works.iterative.mill

import mill._
import mill.scalalib._
import mill.scalalib.publish._

/** Standard publish module trait for Iterative Works projects using Mill.
  * Publishes to the e-BS Nexus repository with signing and staging disabled.
  *
  * To use this trait, extend it in your module and define:
  *   - publishVersion: The version to publish
  *   - pomSettings: The POM settings for the publication
  *
  * Repository URIs default to e-BS Nexus but can be overridden via environment
  * variables or by overriding publishReleaseUri / publishSnapshotUri:
  *   - IW_PUBLISH_RELEASE_URI  (default: https://nexus.e-bs.cz/repository/maven-releases/)
  *   - IW_PUBLISH_SNAPSHOT_URI (default: https://nexus.e-bs.cz/repository/maven-snapshots/)
  *
  * Set environment variables for authentication:
  *   export MILL_SONATYPE_USERNAME=$EBS_NEXUS_USERNAME
  *   export MILL_SONATYPE_PASSWORD=$EBS_NEXUS_PASSWORD
  *
  * Publish with:
  *   mill yourmodule.publish
  */
trait IWPublishModule extends PublishModule {

  /** Release repository URI. Override or set IW_PUBLISH_RELEASE_URI. */
  def publishReleaseUri: String =
    sys.env.getOrElse("IW_PUBLISH_RELEASE_URI", "https://nexus.e-bs.cz/repository/maven-releases/")

  /** Snapshot repository URI. Override or set IW_PUBLISH_SNAPSHOT_URI. */
  def publishSnapshotUri: String =
    sys.env.getOrElse("IW_PUBLISH_SNAPSHOT_URI", "https://nexus.e-bs.cz/repository/maven-snapshots/")

  override def sonatypeLegacyOssrhUri: String = publishReleaseUri
  override def sonatypeCentralSnapshotUri: String = publishSnapshotUri

  /** Disable signing and staging for Nexus. */
  override def publish(
      sonatypeCreds: String = "",
      signed: Boolean = false,
      gpgArgs: String = "",
      release: Boolean = true,
      readTimeout: Int = 30 * 60 * 1000,
      connectTimeout: Int = 30 * 60 * 1000,
      awaitTimeout: Int = 30 * 60 * 1000,
      stagingRelease: Boolean = false
  ): Command[Unit] = super.publish(
    sonatypeCreds,
    signed,
    gpgArgs,
    release,
    readTimeout,
    connectTimeout,
    awaitTimeout,
    stagingRelease
  )

}
