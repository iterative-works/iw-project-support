package works.iterative.sbt

import sbt._
import Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin
import scala.sys.process._
import sbt.nio.file.FileTreeView

class ViteDevServer() {
    private var worker: Option[Worker] = None

    def start(
        workDir: File,
        extraEnv: Seq[(String, String)],
        logger: Logger,
        globalLogger: Logger
    ): Unit =
        this.synchronized {
            stop()
            worker = Some(new Worker(workDir, extraEnv, logger, globalLogger))
        }

    def stop(): Unit = this.synchronized {
        worker.foreach { w =>
            w.stop()
            worker = None
        }
    }

    private class Worker(
        workDir: File,
        extraEnv: Seq[(String, String)],
        logger: Logger,
        globalLogger: Logger
    ) {
        logger.info("Starting vite dev server")
        val command = Seq("yarn", "run", "vite", "dev")
        val process = Process(command, workDir, extraEnv.toSeq: _*).run(
            ProcessLogger(globalLogger.info(_), globalLogger.error(_))
        )

        def stop(): Unit = {
            logger.info("Stopping vite dev server")
            process.destroy()
        }
    }

    override def finalize() = stop()
}

object VitePlugin extends AutoPlugin {
    override lazy val requires = empty // ScalaJSPlugin
    override lazy val trigger = noTrigger

    object autoImport {
        lazy val viteBuild = taskKey[File]("Vite build")
        lazy val viteBasePath = taskKey[String]("Vite base path")
        lazy val viteMonitoredFiles =
            taskKey[Seq[File]]("Files monitored for vite build")
        lazy val viteExtraEnv =
            taskKey[Map[String, String]]("Extra environment for vite build")
        lazy val startViteDev = taskKey[Unit]("Start vite dev mode")
        lazy val stopViteDev = taskKey[Unit]("Stop vite dev mode")
    }

    import autoImport._

    private val viteDist =
        SettingKey[File]("viteDist", "Vite dist directory", KeyRanks.Invisible)

    private val viteDevServer = SettingKey[ViteDevServer](
        "viteDevServer",
        "Global vite dev server",
        KeyRanks.Invisible
    )

    override def projectSettings = Seq(
        viteDist := target.value / "vite",
        viteDevServer := new ViteDevServer(),
        viteBasePath := "/",
        viteExtraEnv := Map.empty,
        startViteDev := {
            val workDir = baseDirectory.value
            val extraEnv = (startViteDev / viteExtraEnv).value
            val prefixedEnv = extraEnv.map { case (k, v) =>
                s"VITE_$k" -> v.toString
            }.toSeq
            val log = streams.value.log
            val globalLog = state.value.globalLogging.full
            val server = viteDevServer.value
            server.start(workDir, prefixedEnv, log, globalLog)
        },
        stopViteDev := {
            viteDevServer.value.stop()
        },
        viteMonitoredFiles := {
            val baseGlob = baseDirectory.value.toGlob
            def baseFiles(pattern: String): Glob = baseGlob / pattern
            val viteConfigs =
                FileTreeView.default.list(
                    List(baseFiles("*.json"), baseFiles("*.js"), baseFiles("*.html"))
                )
            viteConfigs.map(_._1.toFile)
        },
        viteBuild := {
            val s = streams.value
            val dist = viteDist.value
            val base = viteBasePath.value
            val files = viteMonitoredFiles.value
            val extraEnv = (viteBuild / viteExtraEnv).value

            // Create a virtual file representing the configuration
            // FileFunction.cached uses file timestamps, so we write config to a file
            // This ensures the cache invalidates when viteBasePath or viteExtraEnv changes
            val configFile = s.cacheDirectory / "vite-config"
            val configContent = s"BASE=$base\nENV=${extraEnv.toSeq.sortBy(_._1).mkString("\n")}"
            IO.write(configFile, configContent)

            def doBuild() = Process(
                "yarn" :: "run" :: "vite" :: "build" :: "." :: "--outDir" :: dist.toString :: "--base" :: base :: Nil,
                baseDirectory.value,
                extraEnv.map { case (k, v) => s"VITE_$k" -> v.toString }.toSeq: _*
            ) ! s.log
            val cachedFun = FileFunction.cached(s.cacheDirectory / "vite") { _ =>
                doBuild()
                Set(dist)
            }
            // Include configFile in the input set so cache invalidates when config changes
            cachedFun(files.toSet + configFile).head
        },
        (onLoad in Global) := {
            (onLoad in Global).value.compose(
                _.addExitHook {
                    viteDevServer.value.stop()
                }
            )
        }
    )
}

object ScalaJSVitePlugin extends AutoPlugin {
    override lazy val requires = VitePlugin && ScalaJSPlugin
    override lazy val trigger = allRequirements

    import VitePlugin.autoImport._

    override def projectSettings = Seq(
        startViteDev / viteExtraEnv += ("SCALAJS_OUTPUT" -> ((Compile / fastLinkJS / scalaJSLinkerOutputDirectory).value).getAbsolutePath),
        viteMonitoredFiles ++= {
            val linkerDirectory = (Compile / fullLinkJS / scalaJSLinkerOutputDirectory).value
            FileTreeView.default.list(linkerDirectory.toGlob / "*.js").map(_._1.toFile)
        },
        viteBuild / viteExtraEnv ++= Map(
            "SCALAJS_MAIN_JS" -> ((Compile / fullLinkJS / scalaJSLinkerOutputDirectory).value / "main.js").getAbsolutePath,
            "SCALAJS_OUTPUT" -> ((Compile / fullLinkJS / scalaJSLinkerOutputDirectory).value).getAbsolutePath
        ),
        viteBuild := {
            val _ = (Compile / fullLinkJS).value
            viteBuild.value
        }
    )
}
