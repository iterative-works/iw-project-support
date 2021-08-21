sys.props.get("plugin.version") match {
  case Some(x) =>
    addSbtPlugin("works.iterative.sbt" % "sbt-iw-plugin-presets" % x)
  case _ =>
    sys.error(
      """|The system property 'plugin.version' is not defined.
                         |Specify this property using the scriptedLaunchOpts -D.""".stripMargin
    )
}
