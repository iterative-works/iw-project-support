package works.iterative.mill

import mill._
import mill.scalalib._
import mill.scalalib.publish._

/**
 * Standard publish module trait for Iterative Works projects using Mill.
 * This trait sets up standard publishing configuration for IW Maven repositories.
 */
trait IWPublishModule extends PublishModule {
  def publishVersion: T[String]
  
  def pomSettings: T[PomSettings]
  
  /**
   * Determines the repository URL based on version.
   * - Snapshot versions go to the snapshots repository
   * - Release versions go to the releases repository
   */
  def repositoryUrl: T[String] = T {
    if (publishVersion().endsWith("-SNAPSHOT"))
      "https://dig.iterative.works/maven/snapshots"
    else
      "https://dig.iterative.works/maven/releases"
  }
  
  /**
   * Configure publish credentials from environment variables.
   */
  def publishCredentials: T[PublishCredentials] = T {
    val username = sys.env.getOrElse("IW_USERNAME", "")
    val password = sys.env.getOrElse("IW_PASSWORD", "")
    PublishCredentials("GitBucket Maven Repository", "dig.iterative.works", username, password)
  }
  
  /**
   * Configure publication to IW Maven repository.
   */
  def publishTo: T[Option[PublishRepository]] = T {
    Some(PublishRepository(repositoryUrl(), publishCredentials()))
  }
}