# KRar Quick Start Guide

## Project Status
This project provides a solid foundation for a cross-platform archive utility but requires additional implementation to be fully functional.

## What's Working
- Project structure and build configuration
- Basic documentation and architecture planning
- Core data models and interfaces
- Dependency injection setup
- CLI framework

## What Needs Implementation
1. Core archive functionality (compression/decompression)
2. File I/O operations
3. CRC32 verification
4. UI enhancement
5. Build system fixes

## Immediate Next Steps

### 1. Fix Build Issues
```bash
# Check current build status
./gradlew build

# If there are dependency issues, check:
# - gradle/libs.versions.toml
# - composeApp/build.gradle.kts
```

### 2. Implement Core Functionality
Open these files to begin implementation:
- `composeApp/src/commonMain/kotlin/org/sparkystudio/krar/archive/KrarWriterImpl.kt`
- `composeApp/src/commonMain/kotlin/org/sparkystudio/krar/archive/KrarReaderImpl.kt`

### 3. Key Implementation Tasks

#### In KrarWriterImpl.kt:
```kotlin
override suspend fun createArchive(
    files: List<String>,
    archivePath: String,
    dispatcher: CoroutineDispatcher
): Boolean {
    // TODO: 
    // 1. Validate input files exist
    // 2. Create archive file using Okio
    // 3. Write header (KrarHeader)
    // 4. For each file:
    //    - Read file content with Okio
    //    - Compress with Zstd
    //    - Calculate CRC32
    //    - Store file entry metadata
    // 5. Write central directory
    // 6. Write compressed data blocks
    // 7. Handle errors and return success status
}
```

#### In KrarReaderImpl.kt:
```kotlin
override suspend fun extractArchive(
    archivePath: String,
    destinationPath: String,
    dispatcher: CoroutineDispatcher
): Boolean {
    // TODO:
    // 1. Validate archive file exists
    // 2. Read and validate header
    // 3. Parse central directory
    // 4. For each file entry:
    //    - Seek to data offset
    //    - Read compressed data
    //    - Decompress with Zstd
    //    - Verify CRC32
    //    - Write to destination using Okio
    // 5. Handle errors and return success status
}
```

### 4. Test Your Implementation
```bash
# Run unit tests
./gradlew :composeApp:commonTest

# Run JVM tests
./gradlew :composeApp:jvmTest
```

## Key Files to Work With

### Core Implementation
- `composeApp/src/commonMain/kotlin/org/sparkystudio/krar/archive/KrarWriterImpl.kt`
- `composeApp/src/commonMain/kotlin/org/sparkystudio/krar/archive/KrarReaderImpl.kt`

### Testing
- `composeApp/src/commonTest/kotlin/org/sparkystudio/krar/archive/KrarFormatTest.kt`

### CLI Application
- `composeApp/src/jvmMain/kotlin/org/sparkystudio/krar/cli/KrarCli.kt`

### Dependencies
- `gradle/libs.versions.toml` - Version catalog
- `composeApp/build.gradle.kts` - Module configuration

## Useful Commands

### Building
```bash
# Build entire project
./gradlew build

# Build only composeApp module
./gradlew :composeApp:build
```

### Running
```bash
# Run desktop application
./gradlew :composeApp:run

# Run CLI application
./gradlew :composeApp:jvmRun

# Run with arguments
./gradlew :composeApp:jvmRun --args="c test.krar file1.txt"
```

### Testing
```bash
# Run all tests
./gradlew :composeApp:allTests

# Run JVM tests only
./gradlew :composeApp:jvmTest

# Run with continuous testing
./gradlew :composeApp:jvmTest --continuous
```

## Troubleshooting Common Issues

### 1. Dependency Resolution
If you see errors about unresolved dependencies:
- Check that versions in `gradle/libs.versions.toml` are correct
- Ensure dependencies are properly referenced in `composeApp/build.gradle.kts`
- Run `./gradlew --refresh-dependencies`

### 2. Non-ASCII Path Issues
If you see warnings about non-ASCII characters:
- This has been addressed with `android.overridePathCheck=true` in gradle.properties
- Consider moving the project to a path with only ASCII characters if issues persist

### 3. Compose Multiplatform Issues
If Compose references aren't resolving:
- Ensure IntelliJ IDEA or your IDE has properly imported the Gradle project
- Check that all required plugins are applied in build files

## Development Workflow

1. **Pick a task** from the implementation priorities in NEXT_STEPS.md
2. **Create a feature branch** for your work
3. **Implement the functionality** with proper error handling
4. **Write unit tests** to verify your implementation
5. **Run all tests** to ensure nothing is broken
6. **Commit and push** your changes

## Need Help?

### Documentation
- `ARCHITECTURE.md` - Detailed architecture information
- `PROJECT_SUMMARY.md` - Implementation overview
- `NEXT_STEPS.md` - Immediate priorities
- `BUILD_INSTRUCTIONS.md` - Detailed build guidance

### Key Contacts
Project Author: Андрій Будильников

## Ready to Continue Development?

Start by:
1. Running `./gradlew build` to check current status
2. Opening `KrarWriterImpl.kt` and `KrarReaderImpl.kt` to begin core implementation
3. Referencing `NEXT_STEPS.md` for detailed implementation guidance

Happy coding!