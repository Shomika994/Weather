# Weather App - Project Context

## Overview
Android weather application built with modern Android stack.

## Tech Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose
- **DI**: Hilt
- **Networking**: Retrofit + OkHttp
- **Storage**: DataStore Preferences
- **Location**: Google Play Services Location
- **JSON**: Gson

## Architecture
Clean Architecture with layered structure:
```
app/src/main/java/com/example/weather/
├── data/          # Repositories, DTOs, API clients
├── di/            # Hilt dependency injection modules
├── domain/        # Use cases, domain models, repository interfaces
├── presentation/  # ViewModels, Compose UI, screens
└── ui/            # Theme, colors, typography
```

## Key Dependencies
- `androidx.compose.bom` - Compose BOM
- `hilt` - Dependency injection
- `retrofit` + `converter-gson` - HTTP client
- `datastore-preferences` - Local storage
- `play-services-location` - Location services
- `material3` - Material Design 3 components

## Build Config
- `compileSdk = 34`, `targetSdk = 34`, `minSdk = 27`
- Java 11 compatibility
- Compose enabled

## Testing
- Unit tests: JUnit, MockK, Coroutines Test
- UI tests: Compose Test JUnit4
- Location permissions handled via Accompanist

## Project Conventions
- Package by feature in `presentation/`
- Repository pattern in `data/`
- Use cases in `domain/`
- Hilt for all DI
- Compose for all UI
