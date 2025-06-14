package works.iterative.mill

import mill._
import mill.scalalib._
import mill.scalalib.scalafmt._
import coursier.maven.MavenRepository

/** Standard Scala module trait for Iterative Works projects using Mill. This trait sets up standard
  * compiler options and configuration.
  */
trait IWScalaModule extends ScalaModule with ScalafmtModule with IWScalaVersions {

    override def scalaVersion = scala3Version
    
    // Add e-BS Maven repositories for dependency resolution
    override def repositoriesTask = Task.Anon {
        super.repositoriesTask() ++ Seq(
            MavenRepository("https://nexus.e-bs.cz/repository/maven-releases/"),
            MavenRepository("https://nexus.e-bs.cz/repository/maven-snapshots/")
        )
    }

    // Define base compiler options based on Scala version
    def baseScalacOptions = Task {
        if (scalaVersion().startsWith("2.")) {
            Seq(
                "-encoding",
                "utf8",
                "-feature",
                "-unchecked",
                "-language:existentials",
                "-language:experimental.macros",
                "-language:higherKinds",
                "-language:implicitConversions",
                "-Xlint:adapted-args",
                "-Xlint:constant",
                "-Xlint:delayedinit-select",
                "-Xlint:deprecation",
                "-Xlint:doc-detached",
                "-Xlint:implicit-recursion",
                "-Xlint:implicit-not-found",
                "-Xlint:inaccessible",
                "-Xlint:infer-any",
                "-Xlint:missing-interpolator",
                "-Xlint:nullary-unit",
                "-Xlint:option-implicit",
                "-Xlint:package-object-classes",
                "-Xlint:poly-implicit-overload",
                "-Xlint:private-shadow",
                "-Xlint:stars-align",
                "-Xlint:strict-unsealed-patmat",
                "-Xlint:type-parameter-shadow",
                "-Xlint:-byname-implicit",
                "-Wdead-code",
                "-Wextra-implicit",
                "-Wnumeric-widen",
                "-Wvalue-discard",
                "-Wnonunit-statement",
                "-Wunused:nowarn",
                "-Wunused:implicits",
                "-Wunused:explicits",
                "-Wunused:imports",
                "-Wunused:locals",
                "-Wunused:params",
                "-Wunused:patvars",
                "-Wunused:privates"
            )
        } else {
            Seq(
                "-encoding",
                "utf8",
                "-deprecation",
                "-feature",
                "-unchecked",
                "-language:experimental.macros",
                "-language:higherKinds",
                "-language:implicitConversions",
                "-Xkind-projector",
                "-Wvalue-discard",
                "-Wnonunit-statement",
                "-Wunused:implicits",
                "-Wunused:explicits",
                "-Wunused:imports",
                "-Wunused:locals",
                "-Wunused:params",
                "-Wunused:privates"
            )
        }
    }

    // Enable SemanticDB for tooling
    def semanticDbOptions = Task {
        if (scalaVersion().startsWith("2.")) {
            // For Scala 2, semanticdb requires a compiler plugin
            // We'll skip it for now to avoid compilation errors
            Seq.empty
        } else {
            Seq(
                "-Xsemanticdb"
            )
        }
    }

    // Override scalacOptions to include both base options and SemanticDB options
    override def scalacOptions = Task {
        super.scalacOptions() ++ baseScalacOptions() ++ semanticDbOptions()
    }

    // Base test configuration
    trait IWTests extends ScalaTests with ScalafmtModule
}
