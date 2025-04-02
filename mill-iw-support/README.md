# Mill IW Support

This library provides support for using Mill build tool with Iterative Works standards and best practices. It's equivalent to our SBT infrastructure (sbt-iw-projects and sbt-iw-plugin-presets) but for Mill.

## Key Components

### IWMillVersions

Centralized version management for all libraries:

```scala
import works.iterative.mill.IWMillVersions

// Access specific versions
val zioVersion = IWMillVersions.zio // "2.1.16"
```

### IWMillDeps

Standardized dependency management with proper versioning:

```scala
import works.iterative.mill.IWMillDeps

// Use individual dependencies
IWMillDeps.zio          // ivy"dev.zio::zio:2.1.16"
IWMillDeps.zioJson      // ivy"dev.zio::zio-json:0.7.36"
IWMillDeps.tapirCore    // ivy"com.softwaremill.sttp.tapir::tapir-core:1.11.16"

// Use dependency groups
IWMillDeps.useZIO()     // Core ZIO dependencies with test framework
IWMillDeps.useZIOAll()  // Comprehensive ZIO stack
```

### IWScalaModule

Standard module configuration for Scala projects:

```scala
import mill._
import works.iterative.mill._

object myProject extends IWScalaModule {
  // Use default Scala version (3.6.3)
  // and standard compiler options with SemanticDB enabled
  
  def ivyDeps = super.ivyDeps() ++ IWMillDeps.useZIO()
  
  // Standard test module configuration
  object test extends Tests with IWTests
}
```

### IWPublishModule

Standard publishing configuration for IW Maven repositories:

```scala
import mill._
import mill.scalalib.publish._
import works.iterative.mill._

object myProject extends IWScalaModule with IWPublishModule {
  def publishVersion = "0.1.0-SNAPSHOT"
  
  def pomSettings = PomSettings(
    description = "My Project",
    organization = "works.iterative",
    url = "https://github.com/iterative-works/my-project",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("iterative-works", "my-project"),
    developers = Seq(
      Developer("dev", "Developer Name", "https://github.com/dev")
    )
  )
  
  // publishTo is automatically configured based on version
  // (snapshots vs releases)
}
```

## Getting Started

Add the library to your `build.sc` file:

```scala
import $ivy.`works.iterative::mill-iw-support:0.1.0-SNAPSHOT`
import works.iterative.mill._
```

Then extend the appropriate traits in your module definitions:

```scala
object root extends IWScalaModule with IWPublishModule {
  // ... configuration ...
}
```

## Examples

See the `example` directory for a complete working example.

## Benefits of Using Mill

- Faster builds through aggressive caching
- Better IDE support with type-checked build files
- Cleaner syntax and easier configuration
- More intuitive dependency management
- Easier extension with custom tasks