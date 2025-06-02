resolvers += "e-BS Release Repository" at "https://nexus.e-bs.cz/repository/maven-releases/"

resolvers += "e-BS Snapshot Repository" at "https://nexus.e-bs.cz/repository/maven-snapshots/"

addSbtPlugin("works.iterative.sbt" % "sbt-iw-plugin-presets" % "$iw_version$")
