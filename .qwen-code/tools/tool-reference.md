# 🛠️ Cerdita Project - Custom Tools Reference

This directory contains reference implementations for project-specific tools.
These are conceptual tools to guide AI assistance for this project.

---

## Tool 1: Auto-Code-Reviewer

**Purpose:** Review Kotlin code against project conventions before output.

**Checklist:**
- [ ] Follows MVVM pattern
- [ ] Uses Hilt annotations correctly
- [ ] StateFlow for UI state
- [ ] Sealed classes for events/results
- [ ] Proper naming conventions (ViewModel, UseCase, Screen suffixes)
- [ ] No hardcoded strings (use strings.xml)
- [ ] Composables are small and focused
- [ ] Error handling with Result<T>

---

## Tool 2: Project-Structure-Validator

**Purpose:** Verify file placement matches architecture.

**Rules:**
- Entities → `data/local/database/entity/`
- DAOs → `data/local/database/dao/`
- Repositories → `data/repository/`
- UseCases → `domain/usecase/`
- ViewModels → `presentation/viewmodel/`
- Screens → `presentation/ui/screens/`
- Components → `presentation/ui/components/`

---

## Tool 3: Dependency-Graph-Analyzer

**Purpose:** Map dependencies for any file.

**Key Dependencies:**
```
CerditaApplication → Hilt
MainActivity → Hilt, Compose, Navigation
ViewModels → Repositories/UseCases (via Hilt)
Repositories → DAOs, Remote APIs
DAOs → Room
```

---

## Tool 4: Test-Coverage-Enforcer

**Purpose:** Track and enforce test coverage.

**Current Status:** No tests written yet.

**Priority Order:**
1. UseCases (business logic)
2. Repositories (data mapping)
3. ViewModels (state management)
4. Utilities (helpers)

---

## Tool 5: Code-Pattern-Matcher

**Purpose:** Match existing code patterns.

**Golden Patterns:**
```kotlin
// ViewModel pattern
@HiltViewModel
class XViewModel @Inject constructor(
    private val repository: XRepository
) : ViewModel() {
    private val _state = MutableStateFlow(XState())
    val state: StateFlow<XState> = _state.asStateFlow()
}

// UseCase pattern
class XUseCase @Inject constructor(
    private val repository: YRepository
) {
    suspend operator fun invoke(params: Params): Result<Output> {
        // business logic
    }
}

// Repository pattern
class XRepository @Inject constructor(
    private val localDataSource: XLocalDataSource,
    private val remoteDataSource: XRemoteDataSource
) {
    // data operations
}
```

---

## Tool 6: Error-Prediction-Engine

**Purpose:** Predict common errors based on project history.

**High-Risk Areas:**
1. Matrix SDK integration (network, async)
2. Room migrations (schema changes)
3. NtfyService (foreground service, WebSocket)
4. Compose state management (recomposition)

---

## Tool 7: Security-Vulnerability-Scanner

**Purpose:** Detect security issues.

**Checklist:**
- [ ] No hardcoded API keys
- [ ] Tokens stored in EncryptedSharedPreferences
- [ ] No cleartext traffic (usesCleartextTraffic=false)
- [ ] Biometric authentication implemented
- [ ] ProGuard rules for release

---

## Tool 8: Git-History-Learner

**Purpose:** Extract patterns from git history.

**Recent Commits:**
```
6856323 feat: Connect SettingsScreen with SettingsViewModel
226497b docs: Add final implementation status document
253e94c feat: Complete ChatScreen with multimedia support
132f92a feat: Add missing icons, pickers, about screen
cbb002b docs: Add complete implementation summary
```

**Pattern:** Conventional commits with type: description format

---

## Tool 9: Config-Validator

**Purpose:** Validate configuration files.

**Files to Check:**
- `build.gradle.kts` (project + app)
- `settings.gradle.kts`
- `AndroidManifest.xml`
- `gradle.properties`

---

## Tool 10: Build-Impact-Predictor

**Purpose:** Estimate build impact of changes.

**Impact Levels:**
- `build.gradle.kts` changes → Full rebuild required
- Entity/DAO changes → Room reprocessing
- UI changes → Compose recompilation only
- Resource changes → AAPT processing

---

## Tool 11: Team-Convention-Extractor

**Purpose:** Document team conventions.

**Extracted Conventions:**
- Kotlin 100% (no Java)
- Compose for all UI (no XML layouts)
- Hilt for DI (no manual dependency injection)
- Room for database (no raw SQLite)
- StateFlow for reactive streams
- Sealed classes for sum types

---

## Tool 12: Documentation-Sync-Checker

**Purpose:** Ensure docs match implementation.

**Doc Files:**
- README.md (user-facing overview)
- DEVELOPMENT.md (technical specifications)
- IMPLEMENTATION_STATUS.md (feature completion)
- VERIFICATION.md (feature verification)
- QWEN.md (AI context)

---

## Tool 13: Matrix-Integration-Checker

**Purpose:** Verify Matrix SDK integration status.

**Current Status:** 20% complete

**Missing:**
- [ ] Full authentication flow
- [ ] Room management
- [ ] Real-time sync
- [ ] Media upload/download

---

## Tool 14: Ntfy-Config-Validator

**Purpose:** Validate Ntfy notification setup.

**Configuration:**
- 3 servers configured (ntfy.sh, ntfy.services, push.terminal.email)
- Auto-generated topics per device
- WebSocket connection in foreground service
- Topic rotation when approaching limit

---

## Tool 15: Context-Optimizer

**Purpose:** Maximize useful context tokens.

**Strategy:**
1. Load QWEN.md for overview
2. Load smart-context.md for current state
3. Load specific files as needed
4. Compress old conversation turns
5. Keep only relevant file paths
