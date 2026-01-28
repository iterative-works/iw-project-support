package works.iterative.mill

import mill._
import mill.scalalib._
import mill.scalalib.publish._

/** Standard publish module trait for Iterative Works projects using Mill.
  * Publishes to GitHub Packages with signing and staging disabled.
  *
  * To use this trait, extend it in your module and define:
  *   - publishVersion: The version to publish
  *   - pomSettings: The POM settings for the publication
  *   - gitHubPackagesUri: The GitHub Packages URL for your repository
  *
  * Set environment variables for authentication:
  *   export MILL_SONATYPE_USERNAME=$GITHUB_USERNAME
  *   export MILL_SONATYPE_PASSWORD=$GITHUB_TOKEN
  *
  * Publish with:
  *   mill yourmodule.publish
  */
trait IWPublishModule extends PublishModule {

  /** GitHub Packages URL. Override with your repository's URL. */
  def gitHubPackagesUri: String

  override def sonatypeLegacyOssrhUri: String = gitHubPackagesUri
  override def sonatypeCentralSnapshotUri: String = gitHubPackagesUri

  /** Disable signing and staging for GitHub Packages. */
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
