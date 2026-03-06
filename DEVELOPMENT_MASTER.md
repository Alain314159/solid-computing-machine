# 🐷🤗🐨 CERDITA 💕 - DOCUMENTO MAESTRO ULTRA DETALLADO PARA IA
## Guía Completa de Implementación - Producción Ready

**Fecha de Actualización:** 2026-03-06
**Estado:** Implementación en Progreso

---

## 📋 TABLA DE CONTENIDOS

1. INFORMACIÓN GENERAL DEL PROYECTO
2. SISTEMA DE NOTIFICACIONES NTFY (3 TOPICS AUTO)
3. STACK TECNOLÓGICO COMPLETO
4. ESTRUCTURA DE CARPETAS DETALLADA
5. DEPENDENCIAS COMPLETAS
6. CONFIGURACIÓN DEL PROYECTO
7. CÓDIGO NTFY (3 TOPICS AUTOMÁTICOS)
8. CÓDIGO MATRIX
9. BASE DE DATOS ROOM
10. DEPENDENCY INJECTION (HILT)
11. UI/UX - TODAS LAS PANTALLAS
12. ICONOS - ESPECIFICACIONES COMPLETAS
13. ANIMACIONES - LOTTIE JSON
14. TEMAS Y COLORES
15. FEATURES ROMÁNTICOS
16. SETTINGS Y CONFIGURACIÓN
17. ORDEN DE IMPLEMENTACIÓN PASO A PASO
18. CHECKLIST DE VERIFICACIÓN
19. TESTING Y RELEASE

---

## 1. INFORMACIÓN GENERAL DEL PROYECTO

```
┌─────────────────────────────────────────────────────────────────────────┐
│  CERDITA 💕 - ESPECIFICACIONES GENERALES                                │
├─────────────────────────────────────────────────────────────────────────┤
│  NOMBRE Y BRANDING:                                                     │
│  • Nombre: Cerdita 💕                                                   │
│  • Paquete: com.cerdita.app                                             │
│  • Icono: 🐷🤗🐨 (Cerdita y Koalita abrazándose)                       │
│  • Colores principales: Rosa (#FFB6C1), Amarillo (#FFFACD)             │
│                                                                         │
│  PROPÓSITO:                                                             │
│  • Chat romántico privado para parejas (2 usuarios)                    │
│  • Enfoque: Privacidad, romanticismo, simplicidad                      │
│                                                                         │
│  PROTOCOLOS:                                                            │
│  • Mensajes: MATRIX (matrix.org público)                               │
│  • Notificaciones: NTFY.SH (3 topics, rotación automática)             │
│                                                                         │
│  PLATAFORMA:                                                            │
│  • Lenguaje: Kotlin 100%                                                │
│  • UI: Jetpack Compose + Material 3                                     │
│  • Min SDK: Android 8.0 (API 26)                                        │
│  • Target SDK: Android 15 (API 35)                                      │
│                                                                         │
│  COSTO:                                                                 │
│  • $0 (100% gratis, sin servidores propios)                            │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 2. SISTEMA DE NOTIFICACIONES NTFY (3 TOPICS AUTOMÁTICOS)

**ARQUITECTURA DE 3 TOPICS:**
- Topic 1: Principal (se comparte con la pareja)
- Topic 2: Backup 1 (generado automáticamente)
- Topic 3: Backup 2 (generado automáticamente)

**CAPACIDAD TOTAL:**
- 3 topics × 480 mensajes = 1,440 mensajes/día
- 1,440 ÷ 2 personas = 720 por persona/día
- ✅ IMPOSIBLE de alcanzar para 2 personas

---

## 3. STACK TECNOLÓGICO COMPLETO

| Componente | Versión |
|------------|---------|
| Kotlin | 2.1.0 |
| Coroutines | 1.10.1 |
| Jetpack Compose | 1.7.8 |
| Material 3 | 1.3.1 |
| Hilt | 2.55 |
| Room | 2.6.1 |
| Matrix SDK | 0.9.26 |
| OkHttp | 4.12.0 |
| Lottie | 6.6.2 |
| Coil | 2.7.0 |
| Min SDK | 26 |
| Target SDK | 35 |

---

## 4. ESTRUCTURA DE CARPETAS

```
app/src/main/java/com/cerdita/app/
├── CerditaApplication.kt
├── MainActivity.kt
├── data/
│   ├── local/database/ (AppDatabase, DAOs, Entities)
│   ├── local/preferences/ (AuthPreferences, SettingsPreferences)
│   ├── remote/matrix/ (MatrixClient, MatrixRoomManager)
│   └── repository/ (AuthRepository, MessageRepository, etc.)
├── domain/
│   ├── model/ (Message, User, Room, Attachment)
│   └── usecase/ (LoginUseCase, SendMessageUseCase, etc.)
├── presentation/
│   ├── ui/ (theme, components, screens, navigation)
│   └── viewmodel/ (AuthViewModel, ChatViewModel, etc.)
├── di/ (AppModule, NetworkModule, RepositoryModule, UseCaseModule)
├── service/ (NtfyService, MatrixSyncService, BootReceiver)
└── util/ (Constants, DateUtils, RomanticWordsDetector, etc.)
```

---

**Este documento es la guía completa para la implementación.**
**Cualquier desviación debe ser justificada y documentada.**
