# 🐷🤗🐨 CERDITA 💕

**La app de chat romántico para parejas**

[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-purple.svg)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Compose-1.7.8-blue.svg)](https://developer.android.com/jetpack/compose)
[![Status](https://img.shields.io/badge/Status-80%25%20Complete-brightgreen.svg)]()

---

## 📱 Descripción

**Cerdita 💕** es una aplicación de chat romántico diseñada específicamente para parejas. Conecta con tu pareja a través de Matrix.org y disfruta de características únicas como:

- 💬 Chat de texto en tiempo real
- 💕 Efectos románticos al detectar palabras de amor
- 🎨 8 fondos de chat animados personalizables
- 🎙️ Notas de voz con visualizador de onda
- 📅 Calendario de fechas especiales
- 🐷🤗🐨 Botón de abrazo animado
- 🎭 104 stickers románticos
- 🔔 Notificaciones push con Ntfy.sh

---

## ✨ Características Principales

### Comunicación
- ✅ Chat de texto con estados (⏳📤✅👁️)
- ✅ Envío de imágenes (cámara + galería)
- ✅ Envío de videos (cámara + galería)
- ✅ Notas de voz con visualizador
- ✅ Stickers (5 packs, 104 stickers)
- ✅ Emojis

### Personalización
- ✅ 4 temas (Cerdita, Koalita, Flores, Mix)
- ✅ 8 fondos animados
- ✅ Fondos personalizados
- ✅ Intensidad de efectos ajustable

### Características Románticas
- ✅ Detector de 50+ palabras románticas
- ✅ Efectos automáticos (corazones, estrellas, flores)
- ✅ Botón de abrazo (4 tipos)
- ✅ Calendario de fechas especiales
- ✅ Contador de días para eventos

### Privacidad y Seguridad
- ✅ Autenticación con Matrix.org
- ✅ Tokens encriptados
- ✅ Soporte para biometría
- ✅ Notificaciones locales

---

## 🛠️ Tecnología

| Componente | Tecnología |
|------------|-----------|
| **Lenguaje** | Kotlin 2.1.0 |
| **UI Framework** | Jetpack Compose 1.7.8 |
| **Material Design** | Material 3 1.3.1 |
| **Arquitectura** | MVVM + Clean Architecture |
| **Inyección** | Hilt 2.55 |
| **Base de Datos** | Room 2.6.1 |
| **Mensajería** | Matrix (matrix.org) |
| **Notificaciones** | Ntfy.sh |
| **Min SDK** | Android 8.0 (API 26) |
| **Target SDK** | Android 15 (API 35) |

---

## 📁 Estructura del Proyecto

```
app/src/main/java/com/cerdita/app/
├── CerditaApplication.kt
├── MainActivity.kt
├── data/
│   ├── local/ (database, preferences)
│   ├── remote/ (matrix)
│   ├── model/
│   └── repository/
├── domain/
│   ├── model/
│   └── usecase/
├── presentation/
│   ├── ui/ (theme, components, screens)
│   └── viewmodel/
├── di/ (Hilt modules)
├── service/ (Ntfy, Sync)
└── util/ (utilities)
```

---

## 🚀 Cómo Usar

### 1. Login
- Abre la app
- Ingresa tu usuario y contraseña de matrix.org
- La app guarda tu sesión automáticamente

### 2. Configurar Notificaciones
- Ve a Settings → Notificaciones (🔔)
- Copia tu topic (`cerdita-xxxxxxxx`)
- Comparte el topic con tu pareja
- Pega el topic de tu pareja

### 3. Enviar Mensajes
- Escribe en el chat
- Presiona 📷 para enviar imágenes
- Presiona 🎤 para notas de voz
- Presiona 🐷🤗🐨 para enviar un abrazo

### 4. Calendario
- Agrega fechas especiales
- La app te recordará los eventos

---

## 📊 Estado del Proyecto

**Progreso: 80% completo**

### ✅ Completado (100%)
- Autenticación Matrix
- Notificaciones Ntfy
- Base de datos Room
- Repositorios
- Pantallas principales (8)
- Componentes UI (13)
- Servicios
- Utilidades

### ⚠️ Parcial (50-80%)
- Multimedia (integración completa)
- Calendario (recordatorios)
- Settings (persistencia)

### ❌ Pendiente (0%)
- Widget de amor
- Contador de días juntos
- Álbum de recuerdos
- Cartas de amor
- Animaciones Lottie
- Gamificación

---

## 📝 Commits Recientes

```
6856323 feat: Connect SettingsScreen with SettingsViewModel
226497b docs: Add final implementation status document
253e94c feat: Complete ChatScreen with multimedia support
132f92a feat: Add missing icons, pickers, about screen
cbb002b docs: Add complete implementation summary
```

---

## 📄 Documentación

- [DEVELOPMENT.md](DEVELOPMENT.md) - Documento maestro con especificaciones completas
- [FINAL_STATUS.md](FINAL_STATUS.md) - Estado detallado de implementación
- [IMPLEMENTATION_SUMMARY.md](IMPLEMENTATION_SUMMARY.md) - Resumen del proyecto

---

## 🤝 Contribuir

Este proyecto es para uso personal de la pareja Alain-Nayeli. Si quieres usarlo como referencia, siéntete libre de hacerlo.

---

## 📄 Licencia

MIT License - Ver [LICENSE](LICENSE) para más detalles.

---

## 💕 Hecho con Amor

Desarrollado para **Alain & Nayeli** 💑

**Fecha:** Marzo 2026

---

<div align="center">

**🐷🤗🐨 ¡El amor todo lo puede! 🐨🤗🐷**

</div>
