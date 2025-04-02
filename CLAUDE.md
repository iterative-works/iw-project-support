# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands
- Build: `sbt compile`
- Test: `sbt test`
- Single test: `sbt "testOnly fully.qualified.TestName"`
- Test specific method: `sbt "testOnly fully.qualified.TestName -- -t test_name"`
- SBT plugin tests: `sbt scripted`
- Run g8 template test: `sbt g8Test`
- Check formatting: `sbt scalafmtCheck`
- Format code: `sbt scalafmt`
- Fix code style: `sbt scalafixAll`

## Code Style
- Scala 3 is primary (default 3.6.3)
- 100 char line length, 4 space indentation
- ZIO ecosystem is preferred
- Use scalafmt (3.7.x) and scalafix (0.12.x)
- Follow pure functional style with immutable data
- Enable SemanticDB for tooling support
- Prefer early-semver versioning
- Centralize dependency versions in IWMaterialsVersions