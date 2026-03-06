# QWEN.md - Project Context for Cerdita 💕

## Project Overview

**Cerdita 💕** is a romantic chat application designed specifically for couples, built with Kotlin and Jetpack Compose. The app enables real-time messaging through Matrix.org protocol with unique romantic features like love word detection effects, animated backgrounds, voice notes with visualizers, and a special "hug button".

### Key Technologies
- **Language:** Kotlin 2.1.0 (100%)
- **UI Framework:** Jetpack Compose 1.7.8 + Material Design 3 (1.3.1)
- **Architecture:** MVVM + Clean Architecture
- **Dependency Injection:** Hilt 2.55
- **Database:** Room 2.6.1
- **Messaging:** Matrix SDK (matrix.org)
- **Notifications:** Ntfy.sh (WebSocket-based, self-hosted alternative to Firebase)
- **Min SDK:** Android 8.0 (API 26)
- **Target SDK:** Android 15 (API 35)

### Core Features
- Real-time text chat with message states (⏳📤✅👁️)
- Romantic effects detector (50+ love words trigger animations)
- 8 animated chat backgrounds
- Voice notes with wave visualizer
- 104 romantic stickers (5 packs)
- Special events calendar with countdown
- Hug button with 4 animation types
- 4 customizable themes (Cerdita, Koalita, Flores, Mix)
- Push notifications via Ntfy.sh

---

## Building and Running

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 35

### Build Commands

```bash
# Debug build
./gradlew assembleDebug

# Release build (minified with ProGuard)
./gradlew assembleRelease

# Run tests
./gradlew test

# Run instrumentation tests
./gradlew connectedAndroidTest

# Clean build
./gradlew clean build

# Install on connected device
./gradlew installDebug
```

### Running the App
1. Open project in Android Studio
2. Sync Gradle files
3. Select target device/emulator (API 26+)
4. Run `MainActivity` or press Run button

### Project Structure
```
app/src/main/java/com/cerdita/app/
├── CerditaApplication.kt          # Hilt application entry point
├── MainActivity.kt                 # Main activity with Compose
├── data/
│   ├── local/
│   │   ├── database/              # Room entities and DAOs
│   │   └── preferences/           # EncryptedSharedPreferences
│   ├── remote/
│   │   └── matrix/                # Matrix SDK client
│   ├── model/                     # Data models
│   └── repository/                # Repository implementations
├── domain/
│   ├── model/                     # Domain models
│   └── usecase/                   # Use cases (business logic)
├── presentation/
│   ├── ui/
│   │   ├── theme/                 # Compose theme, colors, typography
│   │   ├── components/            # Reusable UI components
│   │   └── screens/               # App screens
│   ├── viewmodel/                 # ViewModels (Hilt)
│   └── navigation/                # Navigation graph
├── di/                            # Hilt dependency injection modules
├── service/                       # Background services (Ntfy, Sync)
└── util/                          # Utilities, extensions, constants
```

---

## Development Conventions

### Code Style
- **Language:** Kotlin with idiomatic patterns (coroutines, flows, sealed classes)
- **Naming:**
  - Classes: PascalCase (`MessageBubble`, `ChatViewModel`)
  - Functions/Properties: camelCase (`sendMessage`, `isLoading`)
  - Constants: SCREAMING_SNAKE_CASE (`MAX_MESSAGE_LENGTH`)
  - Resources: snake_case (`ic_send.xml`, `color_primary.xml`)
- **Package Structure:** Feature-based (`data/`, `domain/`, `presentation/`)

### Architecture Patterns
- **Clean Architecture:** Clear separation between data, domain, and presentation layers
- **MVVM:** ViewModels expose UI state via StateFlow/LiveData
- **Repository Pattern:** Single source of truth for data operations
- **Unidirectional Data Flow:** UI events → ViewModel → State → UI

### Testing Practices
- **Unit Tests:** JUnit for use cases and utilities
- **Integration Tests:** AndroidJUnitRunner for repositories
- **UI Tests:** Compose UI testing framework
- Test files located in `src/test/` (unit) and `src/androidTest/` (instrumentation)

### Git Conventions
- **Commit Format:** `type: description` (e.g., `feat: Add hug button animation`)
- **Types:** `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`
- **Branches:** `main` for production, feature branches for development

### Key Dependencies
```kotlin
// Core
androidx.core:core-ktx:1.15.0
androidx.lifecycle:lifecycle-runtime-ktx:2.8.7
androidx.activity:activity-compose:1.10.1
kotlinx-coroutines-android:1.10.1

// Compose
androidx.compose.ui:ui (BOM 2025.02.00)
androidx.compose.material3:material3:1.3.1
androidx.navigation:navigation-compose:2.8.8

// Matrix SDK
io.element.android:matrix-android-sdk2:0.9.26

// Room
androidx.room:room-ktx:2.6.1 + ksp

// Hilt
com.google.dagger:hilt-android:2.55 + ksp

// Networking
com.squareup.okhttp3:okhttp:4.12.0
com.squareup.okhttp3:logging-interceptor:4.12.0

// Image Loading
io.coil-kt:coil-compose:2.7.0

// Animations
com.airbnb.android:lottie-compose:6.6.2

// Security
androidx.security:security-crypto-ktx:1.1.0-alpha06
androidx.biometric:biometric:1.1.0
```

---

## Important Notes

### Ntfy Notification System
The app uses **Ntfy.sh** instead of Firebase Cloud Messaging for push notifications:
- 1 WebSocket connection per device
- Auto-generates topic on first launch (e.g., `cerdita-abc123def456`)
- Users share topics with their partner
- 3 backup servers configured for redundancy
- Foreground service maintains persistent connection

### Matrix Integration
- Uses matrix.org as the default homeserver
- Authentication via username/password
- Access tokens encrypted in EncryptedSharedPreferences
- Room database stores messages offline

### Current Implementation Status (~65% Complete)
**Completed:**
- ✅ Authentication flow (Login/Register)
- ✅ Ntfy notification system
- ✅ Room database with all entities/DAOs
- ✅ All main screens (Welcome, Login, Register, Chat, Calendar, Settings, Profile, About)
- ✅ 13 UI components (MessageBubble, HugButton, VoiceRecorder, etc.)
- ✅ 8 animated chat backgrounds
- ✅ Sticker picker (5 packs)
- ✅ Romantic word detector (50+ words)

**In Progress:**
- ⚠️ Multimedia integration (image/video picker UI complete, needs ChatScreen integration)
- ⚠️ Settings persistence
- ⚠️ Calendar reminders

**Pending:**
- ❌ Full Matrix SDK integration (real message sending/receiving)
- ❌ Lottie animations (currently using emoji placeholders)
- ❌ Widget de Amor (home screen widget)
- ❌ Days counter for relationship
- ❌ Memory album
- ❌ Love letters feature
- ❌ Gamification features

---

## Common Tasks

### Adding a New Screen
1. Create screen composable in `presentation/ui/screens/`
2. Add route in `presentation/ui/navigation/Screen.kt`
3. Add navigation composable in `AppNavGraph.kt`
4. Create ViewModel if needed (use `@HiltViewModel`)

### Adding a New Database Entity
1. Create `@Entity` data class in `data/local/database/entity/`
2. Add DAO interface in `data/local/database/dao/`
3. Update `AppDatabase.kt` entities list
4. Increment database version and add migration

### Adding a New Use Case
1. Create class in `domain/usecase/` with `invoke()` operator
2. Add binding in `di/UseCaseModule.kt`
3. Inject in ViewModel constructor

### Modifying Theme
- Colors: `presentation/ui/theme/Color.kt`
- Typography: `presentation/ui/theme/Type.kt`
- Theme definition: `presentation/ui/theme/Theme.kt`
- Theme types enum: `presentation/ui/theme/ThemeType.kt`

---

## Documentation Files

### User-Facing Documentation
- `README.md` - User-facing overview and features
- `DEVELOPMENT.md` - Master specification document (1845 lines)
- `IMPLEMENTATION_STATUS.md` - Detailed implementation progress
- `VERIFICATION.md` - Feature completion verification
- `FINAL_STATUS.md` - Final project status summary

### AI Knowledge Base (`.qwen-code/` directory)
- `QWEN.md` - Comprehensive project context (this file)
- `.qwen-code/memory/layer-0-core.json` - Project identity, architecture, patterns
- `.qwen-code/memory/layer-1-session.json` - Session goals and pending tasks
- `.qwen-code/memory/layer-2-history.json` - Learning history and decisions
- `.qwen-code/context/smart-context.md` - Dynamic context by task type
- `.qwen-code/tools/tool-reference.md` - 15 conceptual tools for quality assurance
- `.qwen-code/workflows/workflows.md` - 6 workflows for common tasks
- `.qwen-code/metrics/dashboard.md` - Live metrics dashboard
- `.qwen-code/docs/operating-guidelines.md` - AI behavior guidelines
- `.qwen-code/TRANSFORMATION_REPORT.md` - Transformation execution report

### Using the Knowledge Base

**Starting a Session:**
1. Read `smart-context.md` for current state
2. Check `layer-1-session.json` for pending tasks
3. Reference `layer-0-core.json` for architecture decisions

**During Tasks:**
1. Identify task type → follow corresponding workflow
2. Use tool-reference.md checklists for quality
3. Verify against operating-guidelines.md

**Ending a Session:**
1. Update `layer-2-history.json` with decisions/learnings
2. Update metrics dashboard
3. Document any errors and fixes

---

## Contact & Context
- **Project Name:** Cerdita 💕 (Package: `com.cerdita.app`)
- **Purpose:** Personal romantic chat app for couple Alain & Nayeli
- **Development Date:** March 2026
- **License:** MIT License
- **Status:** 65% complete, functional for demo/UI testing
