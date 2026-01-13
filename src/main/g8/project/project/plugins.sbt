resolvers += "IW releases" at "https://nexus.e-bs.cz/repository/maven-releases/"

resolvers += "IW snapshots" at "https://nexus.e-bs.cz/repository/maven-snapshots/"

addSbtPlugin("works.iterative.sbt" % "sbt-iw-plugin-presets" % "$iw_version$")
