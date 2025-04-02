# TODO: Mill Build Tool Support

## Change Request Reference
- [Mill Build Tool Support](/change-requests/202501-mill.md)

## Completed Tasks
### 1. Research and Planning
- [x] Review all existing SBT plugins and configurations in detail
- [x] Study Mill documentation and examples
- [x] Identify key differences in build definition approach
- [x] Design the structure of the Mill support library
- [x] Document Mill extension development approach

### 2. Project Setup
- [x] Create project structure for mill-iw-support library
- [x] Set up build configuration for Mill library
- [x] Configure cross-version compilation (Scala 2.13 and 3.x)
- [x] Set up publishing to IW Maven repositories

### 3. IWMillVersions Implementation
- [x] Create IWMillVersions object with same versions as IWMaterialsVersions
- [x] Ensure consistent version naming scheme
- [x] Add comprehensive version entries for all libraries
- [x] Add comments for version compatibility notes

### 4. IWMillDeps Implementation
- [x] Create IWMillDeps object with equivalent library definitions
- [x] Implement helper methods (useZIO, useZIOAll, etc.)
- [x] Implement all library categories (ZIO, Akka, HTTP, etc.)
- [x] Add documentation comments for all dependencies

### 5. IWScalaModule Implementation
- [x] Create base IWScalaModule trait
- [x] Configure default compiler options matching SBT configuration
- [x] Setup ScalafmtModule integration
- [x] Configure SemanticDB for tooling support
- [x] Create standard IWTestModule for testing with UTest
- [x] Create IWPublishModule for IW Maven repositories
- [x] Configure snapshot vs release repository handling

### 6. Documentation and Examples
- [x] Create comprehensive README.md for the library
- [x] Create example project with basic single module

## Remaining Tasks

### 1. Cross-Platform Support
- [ ] Implement IWScalaJSModule for JS compilation
- [ ] Configure JS dependencies and tooling support
- [ ] Setup cross-platform dependency handling (JVM/JS)
- [ ] Add Scala.js-specific configurations
- [ ] Implement locale and timezone support equivalent to SBT

### 2. Enhanced Testing Support
- [ ] Create standard IWTestModule for testing with ZIO Test
- [ ] Configure proper test initialization
- [ ] Add test logging configurations
- [ ] Add support for early-semver versioning

### 3. Testing and Validation
- [ ] Create multi-module project test case
- [ ] Create cross-platform (JVM/JS) project test case
- [ ] Implement standard example app with identical SBT and Mill builds
- [ ] Measure and document performance differences
- [ ] Validate dependency resolution works correctly
- [ ] Test compatibility with different Scala versions

### 4. Additional Documentation and Examples
- [ ] Document all available modules and traits in detail
- [ ] Create migration guide from SBT to Mill
- [ ] Create example projects with common configurations:
  - [ ] Multi-module project 
  - [ ] Cross-platform JVM/JS project
  - [ ] ZIO project
  - [ ] Project with JS/Vite integration
- [ ] Update Obsidian documentation with Mill build practices

### 5. Publication and Release
- [ ] Finalize versioning of the Mill support library
- [ ] Document release process
- [ ] Update IW Maven publication scripts
- [ ] Create first release
- [ ] Test with real-world projects
- [ ] Create announcement and presentation for the team

## Priority Order
1. Cross-platform support (JS/JVM)
2. Enhanced testing support with ZIO Test
3. Multi-module examples and testing
4. Comprehensive documentation
5. Publication and release

## Timeline Markers
- Research completion: April 2, 2025 ✅
- Project setup and core components: April 2, 2025 ✅
- Cross-platform support: April 10, 2025
- Testing and validation: April 26, 2025
- Documentation: May 3, 2025
- Release: May 10, 2025