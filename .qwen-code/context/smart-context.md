# 🧠 Smart Context - Cerdita Project

**Last Updated:** 2026-03-06  
**Auto-Refresh:** On each session start

---

## 📊 Current State

### Implementation Status: 65% Complete

| Layer | Status | Priority |
|-------|--------|----------|
| **Infrastructure** | ✅ 100% | - |
| **Authentication** | ✅ 100% | - |
| **Notifications (Ntfy)** | ✅ 100% | - |
| **Database (Room)** | ✅ 100% | - |
| **UI Screens** | ✅ 100% | - |
| **UI Components** | ⚠️ 95% | Medium |
| **Multimedia** | ⚠️ 60% | High |
| **Matrix Integration** | ❌ 20% | Critical |
| **Lottie Animations** | ❌ 0% | Low |
| **Widget** | ❌ 0% | Low |

### Files Changed Recently
- QWEN.md (created comprehensive context)
- 82 Kotlin files analyzed

### Tests Failing
- No test files detected in project yet

### Technical Debt Detected
1. Matrix SDK integration incomplete (critical for production)
2. Settings persistence not connected
3. Calendar reminders not implemented
4. No unit tests written
5. Lottie animations referenced but not created

---

## 🎯 Context by Task Type

### If Bug Fix:
**Related Files to Check:**
- ViewModel for state management issues
- Repository for data flow issues
- DAO for database issues
- MatrixClient for messaging issues

**Common Patterns:**
- Check Hilt injection (@AndroidEntryPoint, @HiltViewModel)
- Verify StateFlow collection in composables
- Check Room entity migrations

### If New Feature:
**Existing Patterns to Follow:**
1. Create domain model in `domain/model/`
2. Create use case in `domain/usecase/`
3. Add repository method in `data/repository/`
4. Create/update ViewModel in `presentation/viewmodel/`
5. Create screen in `presentation/ui/screens/`
6. Add navigation route in `presentation/ui/navigation/`

**Conventions:**
- Use `Result<T>` for operation outcomes
- Use sealed classes for UI events
- Use `@HiltViewModel` for ViewModels
- Use `@Inject` for repository/usecase injection

### If Refactor:
**Impact Analysis:**
- Check all files that import the target
- Verify Hilt dependency graph
- Run build after changes
- Check ProGuard rules for release builds

---

## 📁 Key File Locations

```
app/src/main/java/com/cerdita/app/
├── CerditaApplication.kt          # App entry point
├── MainActivity.kt                 # Main activity
├── data/
│   ├── local/database/             # Room: entities, DAOs
│   ├── local/preferences/          # EncryptedSharedPreferences
│   ├── remote/matrix/              # Matrix SDK client
│   └── repository/                 # Repository implementations
├── domain/
│   ├── model/                      # Domain models
│   └── usecase/                    # Business logic
├── presentation/
│   ├── ui/
│   │   ├── theme/                  # Colors, typography, theme
│   │   ├── components/             # Reusable composables
│   │   └── screens/                # App screens
│   ├── viewmodel/                  # ViewModels
│   └── navigation/                 # Navigation graph
├── di/                             # Hilt modules
├── service/                        # Background services
└── util/                           # Utilities
```

---

## 🔧 Quick Commands

```bash
# Build
./gradlew assembleDebug

# Run tests (when added)
./gradlew test

# Clean build
./gradlew clean build

# Check dependencies
./gradlew app:dependencies
```

---

## 📋 Implementation Priorities

### Critical (Block Production)
1. Complete Matrix SDK integration for real messaging
2. MatrixSyncService for receiving messages
3. Message state updates from Matrix

### High (Core Features)
4. Integrate ImagePicker/VideoPicker in ChatScreen
5. Connect Settings with SettingsRepository
6. Calendar reminders automation

### Medium (UX Improvements)
7. Lottie animations for hug button
8. Days counter for relationship
9. Widget de Amor

### Low (Nice to Have)
10. Gamification features
11. AI/ML features
12. Memory album

---

## 🚨 Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| Hilt injection fails | Check @AndroidEntryPoint on Activity, @HiltViewModel on ViewModel |
| Room migration error | Increment version, add migration or fallbackToDestructiveMigration |
| Compose recomposition loop | Check state hoisting, use derivedStateOf where appropriate |
| Ntfy notifications not received | Verify topic configuration, check foreground service |
