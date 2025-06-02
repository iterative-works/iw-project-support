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
  */
trait IWPublishModule extends PublishModule {

  override def sonatypeUri = "https://nexus.e-bs.cz/repository/maven-releases/"
  override def sonatypeSnapshotUri =
    "https://nexus.e-bs.cz/repository/maven-snapshots/"

}
