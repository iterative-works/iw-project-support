# mill-iw-support

Mill build plugin providing Iterative Works standards for Scala projects.

## Build Commands
- Build: `./mill mill-iw-support.compile` (from `mill-iw-support/` directory)
- Test: `./mill mill-iw-support.test`
- Format: `./mill mill-iw-support.reformat`
- Check format: `./mill mill-iw-support.checkFormat`

## Releasing

See [README.md](README.md#releasing) for the full release process.

Summary: update `publishVersion` in `build.mill` to a release version, commit, tag with `mill-vX.Y.Z`, bump to next SNAPSHOT, commit, push with tags. CI publishes to Maven Central on tag push.
