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
IWMillDeps.zioCore      // List of core ZIO dependencies
IWMillDeps.zioAll       // List of all ZIO dependencies
```

### IWBomModule

Bill of Materials (BOM) module for centralized dependency management:

```scala
import mill._
import works.iterative.mill._

// Define a BOM module
object bom extends IWBomModule {
  def publishVersion = "0.1.0-SNAPSHOT"
  
  def pomSettings = PomSettings(
    // Standard POM settings
  )
  
  // Override with custom dependencies if needed
  override def depManagement = T {
    super.depManagement() ++ Agg(
      ivy"com.example::custom-lib:1.0.0"
    )
  }
}

// Use the BOM in modules
object myModule extends IWScalaModule {
  // Reference the BOM module
  override def bomModuleDeps = Seq(bom)
  
  // No need to specify versions - they come from the BOM
  def ivyDeps = super.ivyDeps() ++ Agg(
    ivy"dev.zio::zio",
    ivy"dev.zio::zio-json"
  )
}
```

### IWScalaModule

Standard module configuration for Scala projects:

```scala
import mill._
import works.iterative.mill._

object myProject extends IWScalaModule {
  // Use default Scala version (3.6.3)
  // and standard compiler options with SemanticDB enabled
  
  def ivyDeps = super.ivyDeps() ++ Agg(
    ivy"${IWMillDeps.zio}",
    ivy"${IWMillDeps.zioJson}"
  )
  
  // Standard test module configuration
  object test extends ScalaTests with TestModule.ZioTest {
    def ivyDeps = super.ivyDeps() ++ Agg(
      ivy"${IWMillDeps.zioTest}",
      ivy"${IWMillDeps.zioTestSbt}"
    )
  }
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

## Dependency Management Approaches

This library provides two approaches for dependency management:

### 1. IWMillDeps (String Constants)

Use the IWMillDeps object to access dependency strings with correct versions:

```scala
def ivyDeps = super.ivyDeps() ++ Agg(
  ivy"${IWMillDeps.zio}",
  ivy"${IWMillDeps.zioJson}"
)
```

### 2. BOM (Bill of Materials)

Use the IWBomModule to centralize dependency versions:

```scala
// Define a BOM module
object bom extends IWBomModule {
  def publishVersion = "0.1.0-SNAPSHOT"
  def pomSettings = PomSettings(...)
}

// Use the BOM in modules
object myModule extends IWScalaModule {
  override def bomModuleDeps = Seq(bom)
  
  // No need to specify versions
  def ivyDeps = super.ivyDeps() ++ Agg(
    ivy"dev.zio::zio",
    ivy"dev.zio::zio-json"
  )
}
```

The BOM approach offers these advantages:
- Enforces consistent versions across modules
- Simplifies version updates
- Controls transitive dependency versions
- Makes build files cleaner (no version strings)
- Can be published as a separate artifact

## Examples

See the `example` directory for a complete working example.

## Benefits of Using Mill

- Faster builds through aggressive caching
- Better IDE support with type-checked build files
- Cleaner syntax and easier configuration
- More intuitive dependency management
- Easier extension with custom tasks