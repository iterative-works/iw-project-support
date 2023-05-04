package works.iterative.sbt

import sbt._
import sbt.Keys.{libraryDependencies, scalaVersion}

trait AkkaLibs {

  self: SlickLibs =>

  object akka {
    val V = IWMaterialsVersions.akka
    val tOrg = "com.typesafe.akka"
    val lOrg = "com.lightbend.akka"

    def akkaMod(name: String): ModuleID =
      tOrg %% s"akka-$name" % V

    def akkaLib(name: String): Def.Setting[_] =
      libraryDependencies += akkaMod(name)

    object modules {
      lazy val actor: ModuleID = akkaMod("actor")
      lazy val actorTyped: ModuleID = akkaMod("actor-typed")
      lazy val stream: ModuleID = akkaMod("stream")
      lazy val persistence: ModuleID = akkaMod("persistence-typed")
      lazy val persistenceQuery: ModuleID = akkaMod("persistence-query")
      lazy val persistenceJdbc: ModuleID =
        lOrg %% "akka-persistence-jdbc" % "5.1.0"
      def persistenceTestKit(configs: Option[String] = None): ModuleID =
        (tOrg %% "akka-persistence-testkit" % V).withConfigurations(configs)
    }

    object libs {
      lazy val actor: Def.Setting[_] = akkaLib("actor")
      lazy val actorTyped: Def.Setting[_] = akkaLib("actor-typed")
      lazy val stream: Def.Setting[_] = akkaLib("stream")
      lazy val persistence: Def.Setting[_] = akkaLib("persistence-typed")
      lazy val persistenceQuery: Def.Setting[_] = akkaLib("persistence-query")
      lazy val persistenceJdbc: Def.Setting[_] =
        libraryDependencies += modules.persistenceJdbc

      def persistenceTestKit(configs: Option[String] = None): Def.Setting[_] =
        libraryDependencies += modules.persistenceTestKit(configs)
    }

    object http {
      val V = IWMaterialsVersions.akkaHttp

      object modules {
        lazy val http: ModuleID = tOrg %% "akka-http" % V
        lazy val sprayJson: ModuleID = tOrg %% "akka-http-spray-json" % V
      }

      object libs {
        lazy val http: Def.Setting[_] = libraryDependencies += modules.http
        lazy val sprayJson: Def.Setting[_] =
          libraryDependencies += modules.sprayJson
      }
    }

    object projection {
      val V = "1.2.5"

      object modules {
        lazy val core: ModuleID =
          lOrg %% "akka-projection-core" % V
        lazy val eventsourced: ModuleID =
          lOrg %% "akka-projection-eventsourced" % V
        lazy val slick: ModuleID =
          lOrg %% "akka-projection-slick" % V
        lazy val jdbc: ModuleID =
          lOrg %% "akka-projection-jdbc" % V
      }

      object libs {
        lazy val core: Def.Setting[_] =
          libraryDependencies += modules.core
        lazy val eventsourced: Def.Setting[_] =
          libraryDependencies += modules.eventsourced
        lazy val slick: Def.Setting[_] =
          libraryDependencies += modules.slick
        lazy val jdbc: Def.Setting[_] =
          libraryDependencies += modules.jdbc
      }
    }

    object profiles {
      // TODO: type safety for requirements
      lazy val eventsourcedJdbcProjection: Def.Setting[_] = {
        val allModules: Seq[ModuleID] = Seq(
          modules.persistenceQuery,
          projection.modules.core,
          projection.modules.eventsourced,
          projection.modules.slick,
          modules.persistenceJdbc
        ) ++ slick.modules.default
        libraryDependencies ++= {
          CrossVersion.partialVersion(scalaVersion.value) match {
            case Some((3, _)) =>
              allModules.map(
                _.cross(librarymanagement.CrossVersion.for3Use2_13)
                  .exclude(
                    "org.scala-lang.modules",
                    "scala-collection-compat_2.13"
                  )
              )
            case _ => allModules
          }
        }
      }
    }
  }

  @deprecated("use akka.libs.actor", "3.0.0")
  lazy val akkaActor: Def.Setting[_] = akka.libs.actor
  @deprecated("use akka.libs.actorTyped", "3.0.0")
  lazy val akkaActorTyped: Def.Setting[_] = akka.libs.actorTyped
  @deprecated("use akka.libs.stream", "3.0.0")
  lazy val akkaStream: Def.Setting[_] = akka.libs.stream
  @deprecated("use akka.libs.persistence", "3.0.0")
  lazy val akkaPersistence: Def.Setting[_] = akka.libs.persistence
  @deprecated("use akka.libs.persistenceQuery", "3.0.0")
  lazy val akkaPersistenceQuery: Def.Setting[_] = akka.libs.persistenceQuery
  @deprecated("use akka.libs.persistenceTestKit", "3.0.0")
  def akkaPersistenceTestKit(configs: Option[String] = None): Def.Setting[_] =
    akka.libs.persistenceTestKit(configs)

  @deprecated("use akka.http.libs.http", "3.0.0")
  lazy val akkaHttp: Def.Setting[_] = akka.http.libs.http
  @deprecated("use akka.http.libs.sprayJson", "3.0.0")
  lazy val akkaHttpSprayJson: Def.Setting[_] = akka.http.libs.sprayJson

}
