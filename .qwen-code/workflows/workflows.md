# ⚡ Workflows for Cerdita Project

**Note:** These are conceptual workflows to guide AI assistance, not auto-executing scripts.

---

## Workflow 1: Feature Implementation

**Trigger:** "Add feature X", "Implement Y", "Create Z screen"

**Steps:**
1. **Analyze Requirement**
   - What is the feature?
   - Which layer does it belong to?
   - Are there similar patterns in the codebase?

2. **Search Existing Patterns**
   - Find similar features for reference
   - Extract naming conventions
   - Identify dependencies

3. **Generate Implementation**
   - Domain model (if needed)
   - UseCase (business logic)
   - Repository method (if data access)
   - ViewModel (state management)
   - Screen/Component (UI)

4. **Generate Tests**
   - UseCase unit tests
   - ViewModel tests
   - UI tests (if critical)

5. **Documentation**
   - Update QWEN.md if architecture changes
   - Add KDoc for public APIs

---

## Workflow 2: Bug Fix

**Trigger:** "Bug in X", "Error when Y", "Fix Z"

**Steps:**
1. **Reproduce Bug**
   - Understand the error
   - Identify affected files
   - Check recent changes

2. **Locate Source**
   - Search related code
   - Check logs/stack trace
   - Identify root cause

3. **Generate Fix**
   - Minimal change to fix issue
   - Consider edge cases
   - Follow existing patterns

4. **Verify Fix**
   - Check related code paths
   - Ensure no regressions
   - Build succeeds

5. **Document Fix**
   - Comment if non-obvious
   - Update docs if behavior changes

---

## Workflow 3: Code Review

**Trigger:** "Review this code", "Check X file", "PR review"

**Steps:**
1. **Security Scan**
   - Check for hardcoded secrets
   - Verify secure storage usage
   - Check network security

2. **Convention Check**
   - Naming conventions
   - File placement
   - Hilt annotations
   - Compose patterns

3. **Test Coverage**
   - Are tests present?
   - Do tests cover edge cases?
   - Will tests catch regressions?

4. **Documentation Check**
   - Public APIs documented
   - Complex logic explained
   - KDoc present

5. **Generate Report**
   - Issues found
   - Suggestions
   - Approval status

---

## Workflow 4: Refactor

**Trigger:** "Refactor X", "Improve Y", "Clean up Z"

**Steps:**
1. **Code Smell Detection**
   - Long functions
   - Duplicated code
   - Complex conditionals
   - Large composables

2. **Impact Analysis**
   - Files that import target
   - Tests that will break
   - Hilt dependencies

3. **Generate Plan**
   - What changes
   - Order of changes
   - Rollback plan

4. **Execute Refactor**
   - Small, incremental changes
   - Build after each change
   - Run tests after each change

5. **Verify**
   - Build succeeds
   - Tests pass
   - Behavior preserved

---

## Workflow 5: Matrix Integration

**Trigger:** "Matrix", "messaging", "send message", "receive message"

**Steps:**
1. **Check Current Status**
   - MatrixClient exists (basic)
   - MatrixSdkClient being developed
   - Auth flow partial

2. **Identify Gap**
   - What's missing for the feature?
   - Which Matrix APIs needed?
   - Error handling requirements

3. **Implementation**
   - Use MatrixSdkClient pattern
   - Handle async operations
   - Update Room database
   - Update UI state

4. **Testing**
   - Mock Matrix responses
   - Test error cases
   - Test offline scenarios

---

## Workflow 6: Ntfy Notification

**Trigger:** "Notification", "Ntfy", "push", "topic"

**Steps:**
1. **Check Configuration**
   - NtfyConfig servers
   - NtfyManager topics
   - NtfyService running

2. **Verify Topic**
   - Topic generated
   - Topic shared with partner
   - Topic persisted

3. **Test Flow**
   - Message sent → Ntfy triggered
   - Notification received
   - Sync with Matrix

---

## Decision Matrix

### When to Act Autonomously
- ✅ Bug fixes in existing code
- ✅ Adding tests
- ✅ Documentation updates
- ✅ Low-risk refactors
- ✅ Linting/formatting

### When to Ask Human
- ⚠️ Changes to build.gradle.kts
- ⚠️ AndroidManifest changes
- ⚠️ Architecture decisions
- ⚠️ Database schema changes
- ⚠️ Same error 3+ times

### When to Escalate Immediately
- 🚨 Security vulnerability
- 🚨 Data loss potential
- 🚨 Build blocking issue
- 🚨 Merge conflict
