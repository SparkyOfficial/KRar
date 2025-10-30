# KRar (Kotlin RAR) Archive Utility

KRar is a modern, cross-platform archive utility built with Kotlin Multiplatform and Compose Multiplatform. It aims to provide an exceptional user experience across all platforms while maintaining technical excellence.

## Project Overview

- **Technology Stack**: Kotlin Multiplatform, Compose Multiplatform
- **File Format**: `.krar`
- **Core Libraries**: Okio Multiplatform, Zstandard (zstd-jni), Kotlin Coroutines, Koin
- **Architecture**: Clean Architecture with MVI/MVVM patterns

## Features

- Cross-platform support (Windows, macOS, Linux, Android, iOS, Web)
- Fast compression using Zstandard algorithm
- Beautiful, intuitive UI built with Compose Multiplatform
- Unified business logic through Kotlin Multiplatform

## Development Plan

### Phase 0: Foundation & Design (1-2 weeks)
- Project setup with Kotlin Multiplatform
- Design of .krar file format v1.0
- UI/UX design in Figma

### Phase 1: Core Engine (CLI Version) (3-4 weeks)
- Integration of Okio for file I/O
- Implementation of KrarWriter and KrarReader classes
- Console application with create, extract, and list commands

### Phase 2: Desktop Application (MVP) (4-6 weeks)
- Compose for Desktop UI implementation
- Archive viewing and creation with drag-and-drop
- Progress indicators and modern interface

### Phase 3: Android Application (MVP) (3-4 weeks)
- Mobile-adapted UI using shared Compose code
- Android Scoped Storage integration
- System integration for opening .krar files

### Phase 4: Release & Future Development (∞)
- Distribution packages for all platforms
- Feature additions (encryption, multi-volume archives, etc.)

## Building and Running

### Desktop (JVM) Application
```shell
./gradlew :composeApp:run
```

### Android Application
```shell
./gradlew :composeApp:assembleDebug
```

### Web Application
```shell
# For Wasm target (modern browsers)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# For JS target (older browsers)
./gradlew :composeApp:jsBrowserDevelopmentRun
```

### iOS Application
Open [/iosApp](./iosApp) directory in Xcode and run from there.