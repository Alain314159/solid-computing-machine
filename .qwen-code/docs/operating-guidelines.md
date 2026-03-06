# 🤖 Operating Guidelines for AI Assistant

**Version:** 1.0  
**Project:** Cerdita 💕  
**Effective:** 2026-03-06

---

## Core Principles

### 1. Verify Before Acting
- Check existing code before generating new code
- Read related files to understand context
- Confirm architecture patterns before implementation

### 2. Learn From Each Interaction
- Record errors in layer-2-history.json
- Identify patterns in mistakes
- Create prevention rules

### 3. Optimize Process
- Measure efficiency (iterations per task)
- Automate repetitive tasks
- Eliminate unnecessary steps

### 4. Anticipate Needs
- Suggest tests after code generation
- Offer documentation updates
- Identify potential issues proactively

### 5. Adapt to Project
- Follow MVVM + Clean Architecture
- Use Hilt for dependency injection
- Match existing naming conventions
- Respect established patterns

---

## Workflow (Mandatory)

```
1. RECEIVE task
2. ANALYZE context (read QWEN.md, smart-context.md)
3. VERIFY memory (check layer-0-core.json)
4. PLAN approach (identify files, patterns)
5. CONSULT existing code (read similar files)
6. GENERATE solution (follow conventions)
7. SELF-REVIEW (check against checklist)
8. DOCUMENT changes (if applicable)
9. UPDATE memory (layer-2-history.json)
10. REPORT result
```

---

## Success Metrics

| Metric | Target | Measurement |
|--------|--------|-------------|
| Code Quality | 95/100 | Auto-review checklist |
| Tests Passing | 100% | Build + test execution |
| Errors per Session | <2 | Error tracking |
| Iterations per Task | <3 | Task completion count |
| Convention Adherence | 100% | Pattern matching |

---

## Mandatory Checks Before Output

### Code Generation Checklist
- [ ] Follows project architecture (MVVM + Clean)
- [ ] Uses correct naming conventions
- [ ] Includes proper Hilt annotations
- [ ] Uses StateFlow for UI state
- [ ] Uses sealed classes for events/results
- [ ] No hardcoded strings
- [ ] Error handling with Result<T>
- [ ] Composables are small and focused

### File Modification Checklist
- [ ] Not in critical_paths (unless explicitly requested)
- [ ] Backup created for significant changes
- [ ] Build will succeed after change
- [ ] Related tests updated
- [ ] Documentation updated

---

## Absolute Prohibitions

❌ Never modify build.gradle.kts without explicit confirmation  
❌ Never change AndroidManifest permissions without confirmation  
❌ Never commit code without verifying build succeeds  
❌ Never ignore security warnings  
❌ Never generate code without self-review  
❌ Never forget to update memory/learning  
❌ Never repeat the same error twice  
❌ Never work without updated context  

---

## When to Ask Human

| Situation | Action |
|-----------|--------|
| Changes to critical files | Ask for confirmation |
| Architecture decisions | Present options, ask for decision |
| Convention conflicts | Clarify which pattern to follow |
| Same error 3+ times | Report issue, ask for guidance |
| Security vulnerabilities | Report immediately |

---

## When to Act Autonomously

| Situation | Action |
|-----------|--------|
| Minor bug fixes | Fix and report |
| Test generation | Create tests |
| Documentation updates | Update docs |
| Low-risk refactors | Refactor with verification |
| Linting/formatting | Auto-fix |

---

## Knowledge Management

### Update Memory After Each Session
```json
// layer-2-history.json
{
  "all_decisions_made": ["add new decisions"],
  "all_errors_made": ["record any errors"],
  "all_fixes_applied": ["record fixes"],
  "pattern_evolution": ["note any new patterns"],
  "knowledge_accumulated": ["add new knowledge"]
}
```

### Context Optimization
- Load QWEN.md for overview
- Load smart-context.md for current state
- Read specific files as needed
- Compress old conversation turns

---

## Evolution Schedule

### Daily
- Update context cache
- Log daily metrics
- Clean temporary files

### Weekly (Every 7 Days)
- Full self-audit
- Error pattern analysis
- Tool effectiveness review
- Generate improvement plan

### Monthly (Every 30 Days)
- Architecture review
- Convention update check
- Performance baseline update
- Major improvement implementation

---

## Handoff Information

### For Other AI Instances
- Read QWEN.md first
- Load smart-context.md
- Check layer-0-core.json for critical info
- Review layer-2-history.json for learned patterns

### For Human Developers
- All context in .qwen-code/ directory
- QWEN.md has comprehensive overview
- Metrics dashboard shows current status
- Workflows document common tasks

---

**Next Auto-Improvement:** 2026-03-13  
**Last Updated:** 2026-03-06
