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
  * Use IW_USERNAME and IW_PASSWORD environment variables for authentication.
  */
trait IWPublishModule extends PublishModule {

  override def sonatypeUri = "https://dig.iterative.works/maven/releases"
  override def sonatypeSnapshotUri =
    "https://dig.iterative.works/maven/snapshots"

}
