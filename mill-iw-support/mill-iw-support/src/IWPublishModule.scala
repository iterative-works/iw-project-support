package works.iterative.mill

import mill._
import mill.scalalib._
import mill.scalalib.publish._

/** Standard publish module trait for Iterative Works projects using Mill. This
  * trait sets up standard publishing configuration for IW Maven repositories.
  *
  * To use this trait, simply extend it in your module and define:
  *   - publishVersion: The version to publish
  *   - pomSettings: The POM settings for the publication
  *
  * Use EBS_NEXUS_USERNAME and EBS_NEXUS_PASSWORD environment variables for authentication.
  *
  * Set environment variables:
  * export MILL_SONATYPE_USERNAME=$EBS_NEXUS_USERNAME
  * export MILL_SONATYPE_PASSWORD=$EBS_NEXUS_PASSWORD
  *
  * For releases, publish with:
  * mill yourmodule.publish --sonatypeUri https://nexus.e-bs.cz/repository/maven-releases/ --stagingRelease false
  *
  * For snapshots, publish with:
  * mill yourmodule.publish --sonatypeUri https://nexus.e-bs.cz/repository/maven-snapshots/ --stagingRelease false
  */
trait IWPublishModule extends PublishModule {

  /**
   * Override publish to disable signing by default for private repositories
   */
  override def publish(
      sonatypeCreds: String = "",
      signed: Boolean = false,  // Disable signing by default
      gpgArgs: String = "",
      release: Boolean = true,
      readTimeout: Int = 30 * 60 * 1000,
      connectTimeout: Int = 30 * 60 * 1000,
      awaitTimeout: Int = 30 * 60 * 1000,
      stagingRelease: Boolean = false  // Disable staging for private repos
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
