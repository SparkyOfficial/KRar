# Immediate Next Steps for KRar Development

## Priority 1: Resolve Build Issues

### 1. Fix Dependency Resolution
- Correct the zstd-jni library reference in build.gradle.kts
- Ensure all Compose dependencies are properly resolved
- Verify Koin and Okio integration

### 2. Address Path Issues
- Consider moving project to path without non-ASCII characters
- Alternative: Further configure gradle.properties for better Unicode support

## Priority 2: Implement Core Archive Functionality

### 1. Complete KrarWriterImpl
```kotlin
class KrarWriterImpl : KrarWriter {
    override suspend fun createArchive(
        files: List<String>,
        archivePath: String,
        dispatcher: CoroutineDispatcher
    ): Boolean {
        // TODO: Implement:
        // 1. Validate input files exist
        // 2. Create archive file
        // 3. Write header
        // 4. Process each file:
        //    - Read with Okio
        //    - Compress with Zstd
        //    - Calculate CRC32
        //    - Store file entry metadata
        // 5. Write central directory
        // 6. Write compressed data blocks
        // 7. Handle errors and cleanup
    }
}
```

### 2. Complete KrarReaderImpl
```kotlin
class KrarReaderImpl : KrarReader {
    override suspend fun extractArchive(
        archivePath: String,
        destinationPath: String,
        dispatcher: CoroutineDispatcher
    ): Boolean {
        // TODO: Implement:
        // 1. Validate archive file exists
        // 2. Read and validate header
        // 3. Parse central directory
        // 4. For each file entry:
        //    - Seek to data offset
        //    - Read compressed data
        //    - Decompress with Zstd
        //    - Verify CRC32
        //    - Write to destination
        // 5. Handle errors and partial extraction
    }
}
```

## Priority 3: CLI Integration

### 1. Connect CLI to Core Implementation
- Inject KrarWriter and KrarReader via Koin
- Implement actual command processing
- Add progress reporting and cancellation support

### 2. Add Error Handling
- File not found errors
- Permission denied errors
- Corrupted archive handling
- Disk space checking

## Priority 4: Testing

### 1. Unit Tests
- Test archive creation with various file types
- Test extraction accuracy
- Test error conditions
- Test edge cases (empty files, large files, etc.)

### 2. Integration Tests
- End-to-end archive creation and extraction
- Cross-platform compatibility testing
- Performance benchmarks

## Priority 5: Desktop UI Enhancement

### 1. Replace Placeholder UI
- Implement file browsing interface
- Add drag-and-drop support
- Create progress dialogs
- Add settings/preferences panel

### 2. Platform Integration
- Native file dialogs
- System tray integration
- File association setup

## Technical Implementation Checklist

### [ ] Fix zstd-jni dependency reference
### [ ] Resolve Compose Multiplatform dependency issues
### [ ] Implement Okio file I/O in KrarWriterImpl
### [ ] Implement Zstd compression in KrarWriterImpl
### [ ] Implement CRC32 calculation in KrarWriterImpl
### [ ] Implement archive file structure writing in KrarWriterImpl
### [ ] Implement header reading in KrarReaderImpl
### [ ] Implement central directory parsing in KrarReaderImpl
### [ ] Implement Zstd decompression in KrarReaderImpl
### [ ] Implement CRC32 verification in KrarReaderImpl
### [ ] Implement file extraction in KrarReaderImpl
### [ ] Connect CLI commands to core implementations
### [ ] Add comprehensive error handling
### [ ] Write unit tests for core functionality
### [ ] Create integration tests
### [ ] Enhance desktop UI with Compose Multiplatform

## Estimated Timeline

### Week 1-2: Build System and Core Implementation
- Resolve all dependency and build issues
- Implement basic KrarWriterImpl and KrarReaderImpl
- Create simple test cases

### Week 3-4: CLI and Testing
- Complete CLI application functionality
- Add comprehensive unit tests
- Fix any core implementation issues

### Week 5-6: Desktop UI and Polish
- Implement full desktop application UI
- Add advanced features
- Performance optimization

## Author
Андрій Будильников