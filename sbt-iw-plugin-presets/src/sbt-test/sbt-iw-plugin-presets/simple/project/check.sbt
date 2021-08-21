lazy val root = project
  .in(file("."))
  .settings(
    TaskKey[Unit]("checkLibraryDependencies") := checkLibraryDependencies.value
  )

def checkLibraryDependencies = Def.task {
  if (
    !libraryDependencies.value.exists(
      _.organization == "io.github.davidgregory084"
    )
  ) {
    sys.error("Library dependencies do not include the default plugins")
  } else {
    ()
  }
}
