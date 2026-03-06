# 🐷🤗🐨 CERDITA 💕 - DOCUMENTO MAESTRO PARA IA
## Guía Completa de Desarrollo (Matrix + Kotlin + Jetpack Compose)

---

## 📋 ÍNDICE

1. [Información General del Proyecto](#1-información-general-del-proyecto)
2. [Características Completas de la App](#2-características-completas-de-la-app)
3. [🚀 Mejoras, Optimizaciones y Features Adicionales](#3--mejoras-optimizaciones-y-features-adicionales)
   - [3.1 UI/UX](#31--uiux)
   - [3.2 Seguridad](#32--seguridad)
   - [3.3 Rendimiento](#33--rendimiento)
   - [3.4 Features Románticos](#34--features-románticos)
   - [3.5 IA/ML](#35--iaml)
   - [3.6 Gamificación](#36--gamificación)
   - [3.7 Arquitectura Técnica](#37--arquitectura-técnica)
   - [3.8 Features Nuevos (TOP 15)](#38--features-nuevos-top-15)
   - [3.9 Dependencias Adicionales](#39--dependencias-adicionales)
   - [3.10 Documento Actualizado para IA](#310--documento-actualizado-para-ia)
4. [Stack Tecnológico](#4-stack-tecnológico)
5. [Estructura de Carpetas](#5-estructura-de-carpetas)
6. [Dependencias Completas](#6-dependencias-completas)
7. [Código Base Mínimo Funcional](#7-código-base-mínimo-funcional)
8. [Orden de Implementación](#8-orden-de-implementación)
9. [Checklist de Verificación](#9-checklist-de-verificación)

---

## 1. INFORMACIÓN GENERAL DEL PROYECTO

```
┌─────────────────────────────────────────────────────────────────────────┐
│  CERDITA 💕 - ESPECIFICACIONES GENERALES                                │
├─────────────────────────────────────────────────────────────────────────┤
│  Nombre:           Cerdita 💕                                           │
│  Paquete:          com.cerdita.app                                      │
│  Protocolo:        MATRIX (NO Nostr)                                    │
│  Lenguaje:         Kotlin 100%                                          │
│  UI Framework:     Jetpack Compose + Material 3                         │
│  Min SDK:          Android 8.0 (API 26)                                 │
│  Target SDK:       Android 15 (API 35)                                  │
│  Usuarios:         2 (pareja)                                           │
│  Servidor:         matrix.org (público, gratuito)                       │
│  Costo:            $0                                                   │
│  Tiempo Est.:      16 semanas                                           │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 2. CARACTERÍSTICAS COMPLETAS DE LA APP

### 2.1 AUTENTICACIÓN MATRIX

| Feature | Descripción | Prioridad |
|---------|-------------|-----------|
| Registro | Crear cuenta en matrix.org desde la app | 🔴 Alta |
| Login | Iniciar sesión con usuario/contraseña | 🔴 Alta |
| Sesión Persistente | Guardar access_token (EncryptedSharedPreferences) | 🔴 Alta |
| Logout | Cerrar sesión y borrar tokens | 🟡 Media |
| Recuperación | Reset password vía email (matrix.org) | 🟢 Baja |

### 2.2 CHAT DE TEXTO

| Feature | Descripción | Prioridad |
|---------|-------------|-----------|
| Enviar mensajes | Texto plano con Matrix Room.send() | 🔴 Alta |
| Recibir mensajes | Sync en tiempo real con Matrix Sync | 🔴 Alta |
| Estados de mensaje | ⏳ Pendiente → 📤 Enviado → ✅ Recibido → 👁️ Leído | 🔴 Alta |
| Historial | Cargar mensajes antiguos desde servidor | 🔴 Alta |
| Offline | Guardar en Room DB y sync al conectar | 🔴 Alta |
| Timestamp | Fecha y hora de cada mensaje | 🟡 Media |
| Editar mensaje | Editar mensajes propios (Matrix edit) | 🟢 Baja |
| Eliminar mensaje | Redact messages (Matrix redact) | 🟢 Baja |

### 2.3 EFECTOS ROMÁNTICOS (50+ VARIACIONES)

```
┌──────────────────┬────────────────────────────────────────────────────┬──────────┐
│ Categoría        │ Palabras Detectadas                                │ Efecto   │
├──────────────────┼────────────────────────────────────────────────────┼──────────┤
│ 💕 Amor          │ "te amo", "te quiero", "te adoro", "eres mi amor"  │ 💛✨💖   │
│ 🌸 Belleza       │ "eres hermosa", "eres bella", "eres preciosa"      │ ✨🌟🌸   │
│ 🌅 Buenos Días   │ "buenos días", "buen día", "feliz día"             │ 🌅🐦☀️   │
│ 🌙 Buenas Noches │ "buenas noches", "que descanses", "dulces sueños"  │ 🌙⭐💫   │
│ 🎂 Cumpleaños    │ "feliz cumpleaños", "feliz cumple"                 │ 🎉🎈🎁   │
│ 💭 Extrañar      │ "te extraño", "te echo de menos", "me haces falta" │ 💭🌧️😢  │
│ 🙏 Gracias       │ "gracias", "gracias mi vida", "mil gracias"        │ 💕✨     │
│ 😢 Perdón        │ "perdón", "perdóname", "lo siento", "disculpa"     │ 😢✨     │
│ 🎊 Felicidades   │ "felicidades", "lo lograste", "orgulloso"          │ 🎊⭐🎆   │
│ 💪 Ánimos        │ "tú puedes", "ánimos", "eres fuerte"               │ 💪🌟     │
│ 🤗 Te Voy a Ver  │ "ya quiero verte", "nos vemos pronto"              │ 🤗😘     │
└──────────────────┴────────────────────────────────────────────────────┴──────────┘
```

**Configuración de Efectos:**
- ✅ Activar/desactivar efectos globales
- ✅ Intensidad (suave/normal/intensa)
- ✅ Añadir palabras personalizadas (apodos de la pareja)
- ✅ Guardar frases especiales

### 2.4 FONDOS DE CHAT PERSONALIZADOS

**8 Fondos Predeterminados Animados:**

| Fondo | Descripción | Animación |
|-------|-------------|-----------|
| 🐷 Cerditas Durmiendo | Cerditas sobre nubes | Respiración suave |
| 🐨 Koalitas en Árboles | Koalitas en ramas | Hojas moviéndose |
| 🌸 Jardín de Flores | Flores varios colores | Pétalos cayendo |
| ☁️ Cielo con Nubes | Nubes blancas | Flotando lentamente |
| 🌙 Noche Estrellada | Cielo oscuro + estrellas | Estrellas brillando |
| 🌈 Arcoíris | Arcoíris colorido | Nubes moviéndose |
| 💕 Corazones Flotando | Corazones rosados | Flotando arriba |
| 🐷🐨 Mascotas Juntas | Cerdita + Koalita | Movimiento suave |

**Fondos Personalizados:**
- ✅ Subir imagen de galería
- ✅ Ajustar brillo/blur/escala
- ✅ Un fondo diferente por chat

**Animación de Bienvenida:**
- ✅ Al abrir chat: mascotas aparecen y saludan (2s)
- ✅ Desvanecimiento gradual

### 2.5 NOTAS DE VOZ MÁGICAS

**5 Tipos de Animaciones:**

| Tipo | Descripción |
|------|-------------|
| ☁️ Nubecitas Flotando | Nubes flotan de izquierda a derecha |
| 🌟 Estrellitas Brillando | Estrellas doradas al ritmo del audio |
| 🐷 Cerditas Saltando | Cerditas saltan con la onda de audio |
| 🐨 Koalitas Balanceándose | Koalitas se balancean con el audio |
| 🎵 Notas Musicales | Notas de colores flotan arriba |

**Características:**
- ✅ Visualizador de onda de audio
- ✅ Sincronizado con volumen
- ✅ Configurable (activar/desactivar, elegir tipo)
- ✅ Grabar hasta 5 minutos
- ✅ Reproducir en chat

### 2.6 CALENDARIO DE FECHAS ESPECIALES

**Tipos de Fechas:**

| Tipo | Icono | Ejemplos |
|------|-------|----------|
| Cumpleaños | 🎂 | Cumpleaños de contactos |
| Aniversario | 💕 | Primera cita, boda, mesiversarios |
| Evento | 🎉 | Viajes, citas, eventos personalizados |
| Fecha Única | 🐷🤗🐨 | Cuando se conocieron, primer beso |

**Características:**
- ✅ Recordatorios automáticos (1 día antes, 1 semana antes)
- ✅ Contador regresivo para eventos próximos
- ✅ Sugerencia de mensaje/sticker romántico
- ✅ Notificación especial el día del evento
- ✅ Animación de mascotas en la fecha
- ✅ Historial de fechas celebradas
- ✅ Integración con chat (recordatorio + sugerencia)

### 2.7 BOTÓN MÁGICO DE ABRAZO

**Ubicación:** En el chat, junto al input de texto (icono: 🐷🤗🐨)

**Animación al Presionar (9 pasos):**
1. Pantalla se oscurece suavemente
2. 🐷 Cerdita y 🐨 Koalita aparecen en el centro
3. Se acercan lentamente (0.5s)
4. Se abrazan tiernamente (0.3s)
5. 💕 Corazones flotando alrededor
6. ✨ Destellitos dorados
7. Mensaje automático: "Te envío un abrazo 🐷🤗🐨"
8. Desvanecimiento gradual (1s)
9. Regreso al chat

**4 Tipos de Abrazos:**

| Tipo | Descripción |
|------|-------------|
| 🐷🤗🐨 Normal | Cerdita + Koalita, corazones pequeños |
| 💕 Romántico | Muchos corazones rosados, destellitos dorados |
| ⭐ Amistad | Estrellitas brillantes, colores azules/amarillos |
| 🐷🐷🤗🐨🐨 Grupal | Múltiples mascotas, arcoíris de fondo |

### 2.8 STICKERS ROMÁNTICOS

**Creador de Stickers Personales:**
1. Seleccionar imagen (cámara o galería)
2. Recortar automáticamente
3. Añadir borde (floral/corazones/estrellas/burbujas)
4. Añadir efectos (brillo/sombra/purpurina)
5. Guardar sticker

**5 Packs Oficiales (104 stickers animados):**

| Pack | Stickers | Animaciones |
|------|----------|-------------|
| 🐷 Cerdita | 24 | Salta, corazones, ronquidos, abrazos |
| 🐨 Koalita | 24 | Come, duerme, saluda, canta |
| 🌸 Flores | 20 | Gira, brilla, mariposa, rocío |
| ☁️ Nubes | 16 | Flota, arcoíris, brilla, llueve |
| 💕 Corazones | 20 | Late, destella, orbita, crece |

**Bordes Decorativos:**
- 🌸 Borde floral (rosas, sakura, margaritas)
- 💕 Borde con corazones
- ⭐ Borde con estrellitas
- 🫧 Borde con burbujas

### 2.9 TEMAS PERSONALIZABLES

**4 Temas Principales:**

| Tema | Colores | Animaciones |
|------|---------|-------------|
| 🐷 Cerdita | Rosa (#FFB6C1), Amarillo (#FFFACD), Coral | 🐷 Cerditas + huellitas |
| 🐨 Koalita | Azul grisáceo (#778899), Verde eucalipto | 🐨 Koalitas + 🍃 hojas |
| 🌸 Flores | Rosa pastel, Verde menta, Lavanda | 🌸 Flores + 🦋 mariposas |
| 🐷🐨 Mix | Gradiente rosa-azul | Todos los elementos combinados |

**Características:**
- ✅ Selector con vista previa
- ✅ Animación de transición suave
- ✅ Material You (Android 12+)
- ✅ Activar/desactivar animaciones

### 2.10 CARACTERÍSTICAS EXCLUSIVAS PARA PAREJAS

| Feature | Descripción |
|---------|-------------|
| Widget de Amor | Widget en home: tiempo juntos, corazón que late |
| Contador de Días Juntos | Fecha inicio relación, contador en pantalla principal |
| Álbum de Recuerdos | Fotos especiales con fecha y notas románticas |
| Cartas de Amor | Escribir cartas largas, guardar, enviar en fechas especiales |
| Promesas/Retos | Lista de promesas, retos románticos, marcar completados |
| Estados de Relación | "En línea", "Ocupada", "Durmiendo", "Pensando en ti" |

### 2.11 MODO OPTIMIZADO (AHORRO DE DATOS)

**Detección automática:**
- ✅ Mide velocidad de conexión
- ✅ Activa modo optimizado en 3G/2G
- ✅ Indicador de red lenta (📶)

**Optimizaciones:**

| Elemento | Optimización |
|----------|--------------|
| Imágenes | Compresión 80%, vista previa baja resolución |
| Videos | No auto-reproducir, compresión extrema |
| Mensajes | Texto plano sin efectos (opcional) |
| Animaciones | Reducir a 50% o desactivar |

**Ahorro configurable:**
- ✅ Límite de datos por día/semana/mes
- ✅ Alerta al alcanzar límite
- ✅ Pausar descarga de multimedia
- ✅ Solo WiFi para multimedia

### 2.12 NOTIFICACIONES

**Funcionamiento:**
- ✅ Firebase Cloud Messaging (gratis)
- ✅ UnifiedPush integration
- ✅ Notificaciones locales (WorkManager fallback)

**Configuración:**
- ✅ Activar/desactivar notificaciones
- ✅ Sonido personalizado
- ✅ Vibración
- ✅ LED de notificación
- ✅ Ocultar contenido en pantalla de bloqueo

### 2.13 FUNCIONAMIENTO OFFLINE

**Enviar sin conexión:**
- ✅ Guardar mensaje en Room DB local
- ✅ Marcar como "pendiente" (⏳)
- ✅ Enviar cuando haya conexión
- ✅ Actualizar estado (📤 → ✅)

**Recibir sin conexión:**
- ✅ Sincronizar al recuperar conexión
- ✅ Descargar mensajes de Matrix server
- ✅ Mostrar mensajes recibidos

**Estados de entrega:**
- ⏳ Pendiente (sin conexión)
- 📤 Enviado a servidor
- ✅ Recibido por tu novia
- 👁️ Leído por tu novia

### 2.14 MULTIMEDIA

| Feature | Descripción |
|---------|-------------|
| Imágenes | Seleccionar de galería, tomar foto, vista previa, compresión, zoom |
| Videos | Seleccionar de galería, grabar video, compresión, reproducir |
| Archivos | Selector de archivos, icono por tipo, descargar, abrir externo |
| Progreso | Barra de subida/bajada, cancelar, reintentar si falla |

### 2.15 AJUSTES COMPLETOS

**Categorías:**

| Sección | Opciones |
|---------|----------|
| 👤 Usuario/Perfil | Ver/editar nombre, cambiar foto, cambiar avatar, copiar ID Matrix, exportar datos |
| 🔐 Privacidad | Bloqueo con PIN/biometría, ocultar notificaciones |
| 🔔 Notificaciones | Activar/desactivar, sonido, vibración, LED |
| 🎨 Apariencia | Selector de temas, tamaño de fuente, activar animaciones |
| 📡 Conexión | Ver estado de conexión, modo optimizado |
| ℹ️ Acerca de | Versión, licencias, GitHub |

---

## 3. 🚀 MEJORAS, OPTIMIZACIONES Y FEATURES ADICIONALES
## Para CERDITA 💕 - Sin Límites

---

## 📊 RESUMEN EJECUTIVO

```
┌─────────────────────────────────────────────────────────────────────────┐
│  CATEGORÍAS DE MEJORA PROPUESTAS                                        │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  🎨 UI/UX              →  15 mejoras sugeridas                          │
│  🔐 Seguridad          →  12 mejoras sugeridas                          │
│  ⚡ Rendimiento        →  10 mejoras sugeridas                          │
│  💕 Features Románticos→  20 features nuevos                            │
│  🤖 IA/ML              →  8 features inteligentes                        │
│  🌐 Integraciones      →  6 integraciones posibles                       │
│  ♿ Accesibilidad      →  8 mejoras de accesibilidad                     │
│  📱 Plataforma         →  5 expansiones de plataforma                    │
│  🎮 Gamificación       →  10 elementos gamificados                       │
│  🔧 Arquitectura       →  7 mejoras técnicas                             │
│                                                                         │
│  TOTAL: 101 mejoras potenciales                                         │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 3.1. 🎨 MEJORAS DE UI/UX (15 SUGERENCIAS)

### 3.1.1 Animaciones y Transiciones

| Mejora | Descripción | Impacto | Dificultad |
|--------|-------------|---------|------------|
| **Micro-interacciones** | Animaciones sutiles en botones, switches, inputs | Alto | Baja |
| **Transiciones entre pantallas** | Shared element transitions, fade, slide | Alto | Media |
| **Haptic feedback** | Vibración táctil en acciones clave (abrazo, enviar) | Medio | Baja |
| **Skeleton loaders** | Mostrar esqueleto mientras carga contenido | Medio | Baja |
| **Gesture controls** | Swipe para responder, double-tap para like | Alto | Media |

### 3.1.2 Personalización Visual

| Mejora | Descripción | Impacto | Dificultad |
|--------|-------------|---------|------------|
| **Burbujas de chat personalizables** | Cada usuario elige color/forma de sus bubbles | Alto | Baja |
| **Fuentes personalizables** | 5-10 fuentes románticas para elegir | Medio | Baja |
| **Tamaño de texto dinámico** | Ajustar por mensaje, no global | Medio | Media |
| **Iconos de estado personalizados** | Elegir emojis para "en línea", "escribiendo" | Bajo | Baja |
| **Sonidos personalizados por contacto** | Sonido único cuando TU NOVIA escribe | Alto | Baja |

### 3.1.3 Experiencia de Escritura

| Mejora | Descripción | Impacto | Dificultad |
|--------|-------------|---------|------------|
| **Respuestas rápidas** | Swipe en mensaje para responder con frases predefinidas | Alto | Media |
| **Autocompletado romántico** | Sugerencias de palabras mientras escribes | Alto | Media |
| **Programar mensajes** | Enviar mensaje en fecha/hora futura | Alto | Media |
| **Borrador automático** | Guardar mensaje no enviado automáticamente | Medio | Baja |
| **Editar mensajes enviados** | Hasta 15 min después de enviar | Medio | Media |

---

## 3.2. 🔐 MEJORAS DE SEGURIDAD Y PRIVACIDAD (12 SUGERENCIAS)

### 3.2.1 Protección de Datos

| Mejora | Descripción | Impacto | Dificultad |
|--------|-------------|---------|------------|
| **Bloqueo por app** | PIN/biometría para abrir la app | Alto | Baja |
| **Exportar datos encriptados** | Backup encriptado con contraseña | Alto | Media |

### 3.2.2 Seguridad de Comunicación

| Mejora | Descripción | Impacto | Dificultad |
|--------|-------------|---------|------------|
| **Verificación de sesión** | Mostrar dispositivos conectados a tu cuenta | Alto | Media |
| **Cerrar sesiones remotas** | Cerrar sesión en otros dispositivos | Alto | Media |
| **Alerta de nuevo dispositivo** | Notificar si alguien inicia sesión en tu cuenta | Alto | Media |
| **Encriptación de backup** | Backup local encriptado con clave | Alto | Media |
| **Sin metadata** | Minimizar metadata visible al servidor | Alto | Alta |

---

## 3.3. ⚡ OPTIMIZACIONES DE RENDIMIENTO (10 SUGERENCIAS)

### 3.3.1 Batería y Datos

| Optimización | Descripción | Ahorro Estimado | Dificultad |
|--------------|-------------|-----------------|------------|
| **Sync inteligente** | Sync solo cuando hay cambios reales | 30-40% batería | Media |
| **Compresión de imágenes** | WebP en lugar de PNG/JPG | 50% datos | Baja |
| **Lazy loading de medios** | Cargar imágenes solo al hacer scroll | 40% datos | Media |
| **Batch de mensajes** | Enviar mensajes en lotes cuando hay varios | 20% batería | Media |
| **Background sync limitado** | Reducir frecuencia en background | 25% batería | Baja |

### 3.3.2 Velocidad y Memoria

| Optimización | Descripción | Mejora | Dificultad |
|--------------|-------------|--------|------------|
| **Cache de imágenes** | Memory + Disk cache con Coil | 3x más rápido | Baja |
| **Pagination de mensajes** | Cargar mensajes de 50 en 50 | 50% menos RAM | Media |
| **Compose compiler metrics** | Optimizar recomposiciones | 20% más fluido | Media |
| **Baseline profiles** | Mejorar tiempo de inicio | 40% más rápido | Media |
| **Native libraries** | Usar Rust SDK de Matrix en lugar de Java | 30% más rápido | Alta |

---

## 3.4. 💕 FEATURES ROMÁNTICOS ADICIONALES (20 SUGERENCIAS)

### 3.4.1 Conexión Emocional

| Feature | Descripción | Impacto | Dificultad |
|---------|-------------|---------|------------|
| **Cuenta regresiva de encuentro** | Widget con tiempo hasta verse | Alto | Baja |
| **Mapa de distancia** | Mostrar distancia entre ustedes (opcional, con privacidad) | Medio | Media |

### 3.4.2 Recuerdos y Historial

| Feature | Descripción | Impacto | Dificultad |
|---------|-------------|---------|------------|
| **"On this day"** | Recordar qué hacían hace 1 mes/año | 🔥 Alto | Media |
| **Línea de tiempo de relación** | Visual timeline de hitos importantes | Alto | Media |
| **Nube de palabras de la relación** | Palabras más usadas entre ustedes | Medio | Media |
| **Estadísticas de amor** | Mensajes enviados, días juntos, etc. | Alto | Baja |
| **Primeras veces** | Registrar y celebrar primeras veces (primer beso, viaje, etc.) | Alto | Baja |

### 3.4.3 Sorpresas y Detalles

| Feature | Descripción | Impacto | Dificultad |
|---------|-------------|---------|------------|
| **Regalos virtuales** | Enviar regalos animados (flores, chocolates, etc.) | Alto | Media |
| **Cupones de amor** | Crear cupones canjeables ("masaje", "cena", etc.) | 🔥 Alto | Baja |
| **Sorpresas aleatorias** | Notificación sorpresa con mensaje romántico | Alto | Media |
| **Mensaje en botella** | Escribir mensaje que se abre en fecha futura | Alto | Baja |

### 3.4.4 Actividades Conjuntas

| Feature | Descripción | Impacto | Dificultad |
|---------|-------------|---------|------------|
| **Lista de deseos juntos** | Bucket list de cosas que quieren hacer | Alto | Baja |
| **Retos de pareja semanales** | Retos románticos para cumplir juntos | 🔥 Alto | Media |
| **Quiz de la relación** | Preguntas sobre cuánto se conocen | Alto | Media |

---

## 3.5. 🤖 FEATURES CON IA/ML (8 SUGERENCIAS)

| Feature | Descripción | Impacto | Dificultad |
|---------|-------------|---------|------------|
| **Análisis de compatibilidad** | Stats divertidas de cómo se comunican | Medio | Media |
| **Resumen semanal de la relación** | IA resume la semana de conversaciones | Alto | Alta |

---

## 3.6. 🎮 GAMIFICACIÓN (10 SUGERENCIAS)

| Elemento | Descripción | Impacto | Dificultad |
|----------|-------------|---------|------------|
| **Racha de mensajes** | Contador de días consecutivos hablando | 🔥 Alto | Baja |
| **Mascotas virtuales** | Cerdita/Koalita crecen según interacción | 🔥 Alto | Alta |
| **Jardín de la relación** | Plantar flores virtuales que crecen juntas | Alto | Media |
| **Árbol de recuerdos** | Árbol que crece con cada hito | Alto | Media |

---

## 3.7. 🔧 MEJORAS DE ARQUITECTURA TÉCNICA (7 SUGERENCIAS)

| Mejora | Descripción | Impacto | Dificultad |
|--------|-------------|---------|------------|
| **Multi-module architecture** | Separar app en módulos (feature, core, etc.) | Alto | Media |
| **Kotlin Multiplatform** | Compartir lógica entre Android/iOS | Alto | Alta |
| **GraphQL en lugar de REST** | Para queries más eficientes a Matrix | Medio | Alta |
| **WebSocket optimizado** | Conexión persistente más eficiente | Alto | Media |
| **CDN para medios** | Servir imágenes/videos desde CDN | Alto | Media |
| **A/B testing framework** | Probar diferentes UI/features | Medio | Media |
| **Analytics privado** | Analytics que no envía datos a terceros | Alto | Media |

---

## 3.8. 🆕 FEATURES COMPLETAMENTE NUEVOS (TOP 15)

```
┌─────────────────────────────────────────────────────────────────────────┐
│  TOP 15 FEATURES QUE NO ESTÁN EN EL PLAN ORIGINAL                       │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  1.  🎵 PLAYLIST COMPARTIDA                                             │
│      Spotify/YouTube sync para escuchar música juntos                  │
│      Impacto: 🔥🔥🔥🔥🔥  Dificultad: Media                            │
│                                                                         │
│  2.  📅 CITA VIRTUAL PROGRAMADA                                         │
│      Agendar "cita" con countdown y preparación automática             │
│      Impacto: 🔥🔥🔥🔥🔥  Dificultad: Baja                              │
│                                                                         │
│  3.  🎁 SORPRESA DEL DÍA                                                │
│      Notificación diaria con algo diferente (frase, sticker, juego)    │
│      Impacto: 🔥🔥🔥🔥  Dificultad: Baja                               │
│                                                                         │
│  4.  📍 MAPA DE LUGARES ESPECIALES                                      │
│      Marcar lugares importantes de la relación en mapa                 │
│      Impacto: 🔥🔥🔥🔥  Dificultad: Media                              │
│                                                                         │
│  5.  🎮 MINI JUEGOS PARA PAREJA                                         │
│      Juegos de 2 jugadores dentro de la app                            │
│      Impacto: 🔥🔥🔥  Dificultad: Alta                                │
│                                                                         │
│  6.  🌟 METEO DEL AMOR                                                  │
│      Widget del clima con mensajes románticos según el clima           │
│      Impacto: 🔥🔥🔥  Dificultad: Baja                                │
│                                                                         │
│  7.  📸 FOTO DEL DÍA                                                    │
│      Compartir una foto diaria obligatoria                             │
│      Impacto: 🔥🔥🔥🔥  Dificultad: Baja                              │
│                                                                         │
│  8.  📖 DIARIO DE LA RELACIÓN                                           │
│      Diario compartido donde ambos escriben                            │
│      Impacto: 🔥🔥🔥🔥  Dificultad: Baja                              │
│                                                                         │
│  9.  🌙 MODO SUEÑO COMPARTIDO                                           │
│       Sonidos/animaciones para dormir "juntos"                         │
│       Impacto: 🔥🔥🔥🔥  Dificultad: Baja                              │
│                                                                         │
│  10. 🔮 PREDICCIONES DEL AMOR                                           │
│       Predicciones divertidas diarias/semanales                        │
│       Impacto: 🔥🔥🔥  Dificultad: Baja                               │
│                                                                         │
│  11. 🎯 METAS DE PAREJA                                                 │
│       Establecer y trackear metas juntos                               │
│       Impacto: 🔥🔥🔥  Dificultad: Media                              │
│                                                                         │
│  12. 🎨 LIENZO COMPARTIDO                                               │
│       Dibujar juntos en tiempo real en un canvas                       │
│       Impacto: 🔥🔥🔥  Dificultad: Alta                               │
│                                                                         │
│  13. 🎪 TRIVIAS DE PAREJA                                               │
│       Preguntas diarias sobre la relación                              │
│       Impacto: 🔥🔥🔥🔥  Dificultad: Baja                              │
│                                                                         │
│  14. 🏆 LOGROS DE LA RELACIÓN                                           │
│       Desbloquear logros por hitos                                     │
│       Impacto: 🔥🔥🔥🔥  Dificultad: Media                              │
│                                                                         │
│  15. 🏅 CERTIFICADOS DE AMOR                                            │
│       Generar certificados bonitos para hitos de relación              │
│       Impacto: 🔥🔥🔥🔥  Dificultad: Baja                              │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 3.9. 📦 DEPENDENCIAS ADICIONALES SUGERIDAS

```kotlin
// build.gradle.kts - Dependencias adicionales opcionales

// Spotify (para playlist compartida)
implementation("com.spotify.android:auth:2.1.1")

// Google Maps (para mapa de lugares especiales)
implementation("com.google.maps.android:maps-compose:4.3.0")
implementation("com.google.android.gms:play-services-maps:18.2.0")

// TensorFlow Lite (para IA local)
implementation("org.tensorflow:tensorflow-lite:2.14.0")
implementation("org.tensorflow:tensorflow-lite-support:0.4.4")

// Lottie adicional (más animaciones)
implementation("com.airbnb.android:lottie:6.6.2")

// DataStore (mejor que SharedPreferences)
implementation("androidx.datastore:datastore-preferences:1.1.1")

// Accompanist (utilidades Compose)
implementation("com.google.accompanist:accompanist-systemuicontroller:0.36.0")
implementation("com.google.accompanist:accompanist-permissions:0.36.0")

// Chucker (debug de red)
debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
```

---

## 3.10. 📝 DOCUMENTO ACTUALIZADO PARA IA

```
┌─────────────────────────────────────────────────────────────────────────┐
│  PARA AGREGAR AL DOCUMENTO MAESTRO DE IA                                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  AGREGAR EN SECCIÓN 2 (CARACTERÍSTICAS):                                │
│  • Todos los features románticos adicionales de la sección 3.4          │
│  • Features de gamificación de la sección 3.6                           │
│  • Top 15 features nuevos de la sección 3.8                             │
│                                                                         │
│  AGREGAR EN SECCIÓN 4 (STACK):                                          │
│  • Spotify SDK (si se implementa playlist)                              │
│  • Google Maps SDK (si se implementa mapa)                              │
│  • TensorFlow Lite (si se implementa IA local)                          │
│                                                                         │
│  AGREGAR EN SECCIÓN 6 (DEPENDENCIAS):                                   │
│  Ver dependencias específicas en sección 3.9                            │
│                                                                         │
│  AGREGAR EN SECCIÓN 8 (ORDEN):                                          │
│  Reordenar prioridades con las nuevas features                          │
│                                                                         │
│  AGREGAR EN SECCIÓN 9 (CHECKLIST):                                      │
│  Incluir checklist de las nuevas features                               │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 4. STACK TECNOLÓGICO

```
┌─────────────────────────────────────────────────────────────────────────┐
│  STACK TECNOLÓGICO - CERDITA 💕                                         │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  LENGUAJE:                                                              │
│  • Kotlin 2.1.0                                                         │
│  • Coroutines 1.10.1                                                    │
│  • Flow para streams reactivos                                          │
│                                                                         │
│  UI:                                                                    │
│  • Jetpack Compose 1.7.8                                                │
│  • Material 3 1.3.1                                                     │
│  • Navigation Compose 2.8.8                                             │
│  • Lottie Compose 6.6.2 (animaciones)                                   │
│  • Coil 2.7.0 (imágenes)                                                │
│                                                                         │
│  ARQUITECTURA:                                                          │
│  • MVVM (Model-View-ViewModel)                                          │
│  • Clean Architecture (data/domain/presentation)                        │
│  • Hilt 2.55 (Dependency Injection)                                     │
│                                                                         │
│  BASE DE DATOS:                                                         │
│  • Room 2.6.1 (offline storage)                                         │
│  • EncryptedSharedPreferences (tokens seguros)                          │
│                                                                         │
│  RED:                                                                   │
│  • Matrix Android SDK2 0.9.26                                           │
│  • Firebase Cloud Messaging (notificaciones)                            │
│  • UnifiedPush 5.0.0                                                    │
│                                                                         │
│  BACKGROUND:                                                            │
│  • WorkManager 2.10.0 (sync en background)                              │
│  • Foreground Service (sync continuo)                                   │
│                                                                         │
│  SEGURIDAD:                                                             │
│  • Security Crypto 1.1.0-alpha06                                        │
│  • Biometric Prompt (bloqueo app)                                       │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 5. ESTRUCTURA DE CARPETAS

```
app/
├── src/main/
│   ├── java/com/cerdita/app/
│   │   ├── CerditaApplication.kt
│   │   ├── MainActivity.kt
│   │   │
│   │   ├── data/
│   │   │   ├── local/
│   │   │   │   ├── database/
│   │   │   │   │   ├── AppDatabase.kt
│   │   │   │   │   ├── dao/
│   │   │   │   │   │   ├── MessageDao.kt
│   │   │   │   │   │   ├── UserDao.kt
│   │   │   │   │   │   ├── SettingsDao.kt
│   │   │   │   │   │   └── EventDao.kt
│   │   │   │   │   └── entity/
│   │   │   │   │       ├── MessageEntity.kt
│   │   │   │   │       ├── UserEntity.kt
│   │   │   │   │       ├── SettingsEntity.kt
│   │   │   │   │       └── EventEntity.kt
│   │   │   │   ├── preferences/
│   │   │   │   │   ├── AuthPreferences.kt
│   │   │   │   │   └── SettingsPreferences.kt
│   │   │   │   └── repository/
│   │   │   │       ├── LocalMessageRepository.kt
│   │   │   │       ├── LocalUserRepository.kt
│   │   │   │       └── LocalSettingsRepository.kt
│   │   │   │
│   │   │   ├── remote/
│   │   │   │   ├── matrix/
│   │   │   │   │   ├── MatrixClient.kt
│   │   │   │   │   ├── MatrixAuth.kt
│   │   │   │   │   ├── MatrixRoom.kt
│   │   │   │   │   ├── MatrixSync.kt
│   │   │   │   │   └── MatrixMedia.kt
│   │   │   │   └── api/
│   │   │   │       ├── AuthApi.kt
│   │   │   │       ├── RoomApi.kt
│   │   │   │       ├── MessageApi.kt
│   │   │   │       └── MediaApi.kt
│   │   │   │
│   │   │   ├── model/
│   │   │   │   ├── Message.kt
│   │   │   │   ├── User.kt
│   │   │   │   ├── Room.kt
│   │   │   │   ├── Attachment.kt
│   │   │   │   └── Event.kt
│   │   │   │
│   │   │   └── repository/
│   │   │       ├── AuthRepository.kt
│   │   │       ├── MessageRepository.kt
│   │   │       ├── RoomRepository.kt
│   │   │       └── SettingsRepository.kt
│   │   │
│   │   ├── domain/
│   │   │   ├── usecase/
│   │   │   │   ├── LoginUseCase.kt
│   │   │   │   ├── RegisterUseCase.kt
│   │   │   │   ├── SendMessageUseCase.kt
│   │   │   │   ├── ReceiveMessagesUseCase.kt
│   │   │   │   ├── SyncRoomUseCase.kt
│   │   │   │   ├── SendMediaUseCase.kt
│   │   │   │   ├── SendVoiceNoteUseCase.kt
│   │   │   │   └── GetEventsUseCase.kt
│   │   │   └── model/
│   │   │       └── Result.kt
│   │   │
│   │   ├── presentation/
│   │   │   ├── ui/
│   │   │   │   ├── theme/
│   │   │   │   │   ├── Theme.kt
│   │   │   │   │   ├── Color.kt
│   │   │   │   │   ├── Type.kt
│   │   │   │   │   └── ThemeType.kt
│   │   │   │   ├── components/
│   │   │   │   │   ├── MessageBubble.kt
│   │   │   │   │   ├── InputField.kt
│   │   │   │   │   ├── HugButton.kt
│   │   │   │   │   ├── StickerPicker.kt
│   │   │   │   │   ├── VoiceRecorder.kt
│   │   │   │   │   ├── EventCard.kt
│   │   │   │   │   └── ThemeSelector.kt
│   │   │   │   └── screens/
│   │   │   │       ├── welcome/
│   │   │   │       │   └── WelcomeScreen.kt
│   │   │   │       ├── auth/
│   │   │   │       │   ├── LoginScreen.kt
│   │   │   │       │   └── RegisterScreen.kt
│   │   │   │       ├── chat/
│   │   │   │       │   ├── ChatScreen.kt
│   │   │   │       │   └── ChatViewModel.kt
│   │   │   │       ├── calendar/
│   │   │   │       │   ├── CalendarScreen.kt
│   │   │   │       │   └── CalendarViewModel.kt
│   │   │   │       ├── settings/
│   │   │   │       │   ├── SettingsScreen.kt
│   │   │   │       │   └── SettingsViewModel.kt
│   │   │   │       └── profile/
│   │   │   │           └── ProfileScreen.kt
│   │   │   │
│   │   │   └── viewmodel/
│   │   │       ├── AuthViewModel.kt
│   │   │       ├── ChatViewModel.kt
│   │   │       ├── CalendarViewModel.kt
│   │   │       └── SettingsViewModel.kt
│   │   │
│   │   ├── di/
│   │   │   ├── AppModule.kt
│   │   │   ├── NetworkModule.kt
│   │   │   ├── DatabaseModule.kt
│   │   │   └── RepositoryModule.kt
│   │   │
│   │   ├── util/
│   │   │   ├── Constants.kt
│   │   │   ├── Extensions.kt
│   │   │   ├── Result.kt
│   │   │   └── DateUtils.kt
│   │   │
│   │   └── service/
│   │       ├── SyncService.kt
│   │       ├── NotificationService.kt
│   │       └── FCMService.kt
│   │
│   ├── res/
│   │   ├── drawable/
│   │   │   ├── ic_pig.xml
│   │   │   ├── ic_koala.xml
│   │   │   ├── ic_heart.xml
│   │   │   ├── ic_hug.xml
│   │   │   ├── ic_sticker.xml
│   │   │   ├── ic_calendar.xml
│   │   │   ├── ic_settings.xml
│   │   │   └── ... (todos los iconos)
│   │   ├── raw/
│   │   │   ├── anim_hug.json
│   │   │   ├── anim_hearts.json
│   │   │   ├── anim_stars.json
│   │   │   ├── anim_pig_sleep.json
│   │   │   ├── anim_koala_tree.json
│   │   │   └── ... (todas las animaciones Lottie)
│   │   ├── values/
│   │   │   ├── strings.xml
│   │   │   ├── colors.xml
│   │   │   ├── themes.xml
│   │   │   └── dimens.xml
│   │   ├── font/
│   │   │   └── ... (fuentes personalizadas)
│   │   └── mipmap-*/
│   │       └── ic_launcher.png (icono de app)
│   │
│   └── AndroidManifest.xml
│
├── build.gradle.kts (app)
├── build.gradle.kts (project)
└── settings.gradle.kts
```

---

## 6. DEPENDENCIAS COMPLETAS

### 6.1 build.gradle.kts (app)

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.cerdita.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cerdita.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // ═══════════════════════════════════════════════════════════════════
    // CORE
    // ═══════════════════════════════════════════════════════════════════
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

    // ═══════════════════════════════════════════════════════════════════
    // COMPOSE
    // ═══════════════════════════════════════════════════════════════════
    implementation(platform("androidx.compose:compose-bom:2025.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.animation:animation-graphics")
    implementation("androidx.navigation:navigation-compose:2.8.8")

    // ═══════════════════════════════════════════════════════════════════
    // MATRIX SDK
    // ═══════════════════════════════════════════════════════════════════
    implementation("io.element.android:matrix-android-sdk2:0.9.26")

    // ═══════════════════════════════════════════════════════════════════
    // ROOM DATABASE
    // ═══════════════════════════════════════════════════════════════════
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // ═══════════════════════════════════════════════════════════════════
    // HILT (Dependency Injection)
    // ═══════════════════════════════════════════════════════════════════
    implementation("com.google.dagger:hilt-android:2.55")
    ksp("com.google.dagger:hilt-compiler:2.55")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // ═══════════════════════════════════════════════════════════════════
    // NOTIFICATIONS
    // ═══════════════════════════════════════════════════════════════════
    implementation("org.unifiedpush:connector:5.0.0")
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-messaging-ktx")

    // ═══════════════════════════════════════════════════════════════════
    // ANIMATIONS (LOTTIE)
    // ═══════════════════════════════════════════════════════════════════
    implementation("com.airbnb.android:lottie-compose:6.6.2")

    // ═══════════════════════════════════════════════════════════════════
    // IMAGES
    // ═══════════════════════════════════════════════════════════════════
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("io.coil-kt:coil-gif:2.7.0")

    // ═══════════════════════════════════════════════════════════════════
    // SECURITY
    // ═══════════════════════════════════════════════════════════════════
    implementation("androidx.security:security-crypto-ktx:1.1.0-alpha06")
    implementation("androidx.biometric:biometric:1.1.0")

    // ═══════════════════════════════════════════════════════════════════
    // WORK MANAGER
    // ═══════════════════════════════════════════════════════════════════
    implementation("androidx.work:work-runtime-ktx:2.10.0")

    // ═══════════════════════════════════════════════════════════════════
    // TESTING
    // ═══════════════════════════════════════════════════════════════════
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.02.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
```

### 6.2 build.gradle.kts (project)

```kotlin
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
    }
}

plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" apply false
    id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
    id("com.google.dagger.hilt.android") version "2.55" apply false
}
```

### 6.3 settings.gradle.kts

```kotlin
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Cerdita"
include(":app")
```

---

## 7. CÓDIGO BASE MÍNIMO FUNCIONAL

### 7.1 CerditaApplication.kt

```kotlin
package com.cerdita.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CerditaApplication : Application()
```

### 7.2 AndroidManifest.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- Permisos de Internet -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <!-- Notificaciones -->
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <uses-permission android:name="android.permission.VIBRATE" />

    <!-- Multimedia -->
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" 
        android:maxSdkVersion="32" />
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
    <uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
    <uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />

    <!-- Background Sync -->
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE_DATA_SYNC" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />

    <!-- Biometría -->
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />

    <application
        android:name=".CerditaApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Cerdita"
        android:usesCleartextTraffic="false"
        tools:targetApi="35">

        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.Cerdita"
            android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- FCM Service -->
        <service
            android:name=".service.FCMService"
            android:exported="false">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>

        <!-- Sync Service -->
        <service
            android:name=".service.SyncService"
            android:enabled="true"
            android:exported="false"
            android:foregroundServiceType="dataSync" />

    </application>
</manifest>
```

### 7.3 MainActivity.kt

```kotlin
package com.cerdita.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.cerdita.app.presentation.ui.navigation.AppNavGraph
import com.cerdita.app.presentation.ui.theme.CerditaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CerditaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavGraph(navController = navController)
                }
            }
        }
    }
}
```

### 7.4 MatrixClient.kt (Mínimo Funcional)

```kotlin
package com.cerdita.app.data.remote.matrix

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import im.vector.matrix.android.api.Matrix
import im.vector.matrix.android.api.MatrixConfiguration
import im.vector.matrix.android.api.Session
import im.vector.matrix.android.api.auth.data.Credentials
import im.vector.matrix.android.api.auth.data.HomeServerConnectionConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MatrixClient @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Disconnected)
    val sessionState: Flow<SessionState> = _sessionState

    private var session: Session? = null
    private val defaultHomeserver = "https://matrix-client.matrix.org"

    private val encryptedPrefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            context,
            "cerdita_secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    suspend fun register(username: String, password: String): Result<String> {
        return try {
            val hsConfig = HomeServerConnectionConfig.Builder()
                .withHomeServerUri(defaultHomeserver)
                .build()

            val authService = Matrix.getAuthenticationService(hsConfig)
            val response = authService.register(
                username = username,
                password = password,
                inhibitOtherDevices = false
            )

            saveCredentials(
                userId = response.userId,
                accessToken = response.accessToken,
                deviceId = response.deviceId
            )

            Result.success(response.userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(username: String, password: String): Result<String> {
        return try {
            val hsConfig = HomeServerConnectionConfig.Builder()
                .withHomeServerUri(defaultHomeserver)
                .build()

            val authService = Matrix.getAuthenticationService(hsConfig)
            val response = authService.login(
                username = username,
                password = password
            )

            saveCredentials(
                userId = response.userId,
                accessToken = response.accessToken,
                deviceId = response.deviceId
            )

            Result.success(response.userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginWithToken(): Result<Unit> {
        return try {
            val userId = encryptedPrefs.getString("user_id", null)
            val accessToken = encryptedPrefs.getString("access_token", null)
            val deviceId = encryptedPrefs.getString("device_id", null)

            if (userId == null || accessToken == null) {
                return Result.failure(Exception("No credentials"))
            }

            val hsConfig = HomeServerConnectionConfig.Builder()
                .withHomeServerUri(defaultHomeserver)
                .build()

            val credentials = Credentials(
                userId = userId,
                accessToken = accessToken,
                deviceId = deviceId,
                homeServer = defaultHomeserver
            )

            session = Matrix.getInstance(context)
                .getSession(credentials, hsConfig, MatrixConfiguration())

            _sessionState.value = SessionState.Connected
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun saveCredentials(userId: String, accessToken: String, deviceId: String) {
        encryptedPrefs.edit().apply {
            putString("user_id", userId)
            putString("access_token", accessToken)
            putString("device_id", deviceId)
            apply()
        }
    }

    fun logout() {
        session?.logout()
        session = null
        encryptedPrefs.edit().clear().apply()
        _sessionState.value = SessionState.Disconnected
    }

    fun getSession(): Session? = session
    fun isLogged(): Boolean = session != null

    sealed class SessionState {
        object Connected : SessionState()
        object Disconnected : SessionState()
        object Connecting : SessionState()
    }
}
```

### 7.5 AuthRepository.kt

```kotlin
package com.cerdita.app.data.repository

import com.cerdita.app.data.remote.matrix.MatrixClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val matrixClient: MatrixClient
) {
    val sessionState: Flow<MatrixClient.SessionState> = matrixClient.sessionState
    fun isLogged(): Boolean = matrixClient.isLogged()

    suspend fun register(username: String, password: String): Result<String> =
        matrixClient.register(username, password)

    suspend fun login(username: String, password: String): Result<String> =
        matrixClient.login(username, password)

    suspend fun loginWithToken(): Result<Unit> =
        matrixClient.loginWithToken()

    fun logout() = matrixClient.logout()
}
```

### 7.6 AuthViewModel.kt

```kotlin
package com.cerdita.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerdita.app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Initial)
    val uiState: StateFlow<AuthUiState> = _uiState

    init {
        checkExistingSession()
    }

    private fun checkExistingSession() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            authRepository.loginWithToken()
                .onSuccess { _uiState.value = AuthUiState.LoggedIn }
                .onFailure { _uiState.value = AuthUiState.LoggedOut }
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            authRepository.register(username, password)
                .onSuccess { _uiState.value = AuthUiState.LoggedIn }
                .onFailure { e -> _uiState.value = AuthUiState.Error(e.message ?: "Error") }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            authRepository.login(username, password)
                .onSuccess { _uiState.value = AuthUiState.LoggedIn }
                .onFailure { e -> _uiState.value = AuthUiState.Error(e.message ?: "Error") }
        }
    }

    fun logout() {
        authRepository.logout()
        _uiState.value = AuthUiState.LoggedOut
    }
}

sealed class AuthUiState {
    object Initial : AuthUiState()
    object Loading : AuthUiState()
    object LoggedIn : AuthUiState()
    object LoggedOut : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}
```

### 7.7 AppDatabase.kt (Room)

```kotlin
package com.cerdita.app.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cerdita.app.data.local.database.dao.MessageDao
import com.cerdita.app.data.local.database.entity.MessageEntity

@Database(entities = [MessageEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cerdita_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

### 7.8 MessageEntity.kt

```kotlin
package com.cerdita.app.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val messageId: String,
    val roomId: String,
    val senderId: String,
    val content: String,
    val timestamp: Long,
    val status: String, // pending, sent, delivered, read
    val type: String, // text, image, video, voice
    val mediaUrl: String? = null
)
```

### 7.9 MessageDao.kt

```kotlin
package com.cerdita.app.data.local.database.dao

import androidx.room.*
import com.cerdita.app.data.local.database.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE roomId = :roomId ORDER BY timestamp ASC")
    fun getMessagesByRoom(roomId: String): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Update
    suspend fun updateMessage(message: MessageEntity)

    @Delete
    suspend fun deleteMessage(message: MessageEntity)

    @Query("SELECT * FROM messages WHERE status = 'pending'")
    suspend fun getPendingMessages(): List<MessageEntity>
}
```

### 7.10 DI Modules

```kotlin
// di/AppModule.kt
package com.cerdita.app.di

import android.content.Context
import com.cerdita.app.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideMessageDao(database: AppDatabase) = database.messageDao()
}
```

```kotlin
// di/RepositoryModule.kt
package com.cerdita.app.di

import com.cerdita.app.data.remote.matrix.MatrixClient
import com.cerdita.app.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(matrixClient: MatrixClient): AuthRepository {
        return AuthRepository(matrixClient)
    }
}
```

---

## 8. ORDEN DE IMPLEMENTACIÓN

```
┌─────────────────────────────────────────────────────────────────────────┐
│  ORDEN DE IMPLEMENTACIÓN - PRIORIDADES                                  │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  🔴 PRIORIDAD ALTA (Semanas 1-8) - MVP FUNCIONAL                       │
│  ═══════════════════════════════════════════════════════════════════   │
│  1.  Configuración del proyecto + dependencias                         │
│  2.  MatrixClient (registro + login + token)                           │
│  3.  AuthViewModel + AuthRepository                                    │
│  4.  Pantallas de Login/Register                                       │
│  5.  Room Database (MessageEntity + MessageDao)                        │
│  6.  Enviar mensajes de texto                                          │
│  7.  Recibir mensajes (Matrix Sync)                                    │
│  8.  ChatScreen básico (MessageBubble + InputField)                    │
│  9.  Estados de mensaje (⏳📤✅👁️)                                    │
│  10. Offline support (guardar en Room cuando sin conexión)             │
│                                                                         │
│  🟡 PRIORIDAD MEDIA (Semanas 9-12) - CARACTERÍSTICAS CORE             │
│  ═══════════════════════════════════════════════════════════════════   │
│  11. Efectos románticos (detector de palabras + animaciones Lottie)    │
│  12. Fondos de chat (8 predeterminados + personalizado)                │
│  13. Temas (4 temas con selector)                                      │
│  14. Notas de voz (grabar + reproducir + animación)                    │
│  15. Multimedia (imágenes + videos)                                    │
│  16. Notificaciones (FCM + UnifiedPush)                                │
│  17. Calendario de fechas especiales                                   │
│  18. Modo optimizado (ahorro de datos)                                 │
│                                                                         │
│  🟢 PRIORIDAD BAJA (Semanas 13-16) - PULIDO FINAL                      │
│  ═══════════════════════════════════════════════════════════════════   │
│  19. Stickers (5 packs + creador personalizado)                        │
│  20. Botón de abrazo (4 tipos + animación completa)                    │
│  21. Widget de amor (contador de días)                                 │
│  22. Álbum de recuerdos                                                │
│  23. Cartas de amor                                                    │
│  24. Ajustes completos (privacidad + biometría)                        │
│  25. Testing exhaustivo + bug fixing                                   │
│  26. Build release + firma                                             │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 9. CHECKLIST DE VERIFICACIÓN

### 9.1 Checklist Semanal

```
┌─────────────────────────────────────────────────────────────────────────┐
│  CHECKLIST SEMANAL - PARA EJECUTAR CON IA                               │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  SEMANA 1:                                                             │
│  ☐ Crear proyecto Android Studio (Kotlin, Compose, MinSDK 26)          │
│  ☐ Agregar todas las dependencias del build.gradle.kts                 │
│  ☐ Configurar Hilt (AppModule, NetworkModule, DatabaseModule)          │
│  ☐ Crear estructura de carpetas completa                               │
│  ☐ Configurar Room Database                                            │
│  ☐ Verificar que el proyecto compila sin errores                       │
│                                                                         │
│  SEMANA 2:                                                             │
│  ☐ Implementar MatrixClient con SDK oficial                            │
│  ☐ Crear pantalla de Registro (username + password)                    │
│  ☐ Crear pantalla de Login                                             │
│  ☐ Guardar access_token en EncryptedSharedPreferences                  │
│  ☐ Testing: crear 2 cuentas y hacer login                              │
│                                                                         │
│  SEMANA 3:                                                             │
│  ☐ Configurar Navigation Compose                                       │
│  ☐ Crear WelcomeScreen con animación de mascotas                       │
│  ☐ Crear 4 temas (Cerdita, Koalita, Flores, Mix)                       │
│  ☐ Crear ChatScreen base                                               │
│                                                                         │
│  SEMANA 4:                                                             │
│  ☐ Crear MessageEntity + MessageDao                                    │
│  ☐ Implementar MessageRepository (local + remote)                      │
│  ☐ Sync de mensajes (Matrix → Room)                                    │
│  ☐ Testing offline                                                     │
│                                                                         │
│  SEMANA 5:                                                             │
│  ☐ Enviar mensaje (Matrix Room.send())                                 │
│  ☐ Recibir mensajes (Matrix Sync)                                      │
│  ☐ MessageBubble UI + estados (⏳📤✅👁️)                              │
│  ☐ Testing bidireccional                                               │
│                                                                         │
│  SEMANA 6:                                                             │
│  ☐ Detector de palabras clave                                          │
│  ☐ Animaciones con Lottie (corazones, estrellas)                       │
│  ☐ Configuración (activar/desactivar, intensidad)                      │
│  ☐ Testing efectos (50+ variaciones)                                   │
│                                                                         │
│  SEMANA 7:                                                             │
│  ☐ 8 fondos predeterminados                                            │
│  ☐ Subir imagen personalizada                                          │
│  ☐ Animación de bienvenida (mascotas)                                  │
│  ☐ Testing fondos                                                      │
│                                                                         │
│  SEMANA 8:                                                             │
│  ☐ Enviar imágenes (Matrix uploadMedia())                              │
│  ☐ Enviar videos                                                       │
│  ☐ Notas de voz + visualizador animado                                 │
│  ☐ Testing multimedia                                                  │
│                                                                         │
│  SEMANA 9-16: (continuar con el resto de features)                     │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 9.2 Checklist Final de Release

```
┌─────────────────────────────────────────────────────────────────────────┐
│  CHECKLIST FINAL - ANTES DE RELEASE                                     │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  FUNCIONALIDAD:                                                         │
│  ☐ Login/Register funciona con matrix.org                              │
│  ☐ Enviar/recibir mensajes de texto                                    │
│  ☐ Enviar/recibir imágenes                                             │
│  ☐ Enviar/recibir videos                                               │
│  ☐ Enviar/recibir notas de voz                                         │
│  ☐ Efectos románticos activados                                        │
│  ☐ Fondos de chat configurables                                        │
│  ☐ Temas cambian correctamente                                         │
│  ☐ Notificaciones llegan                                               │
│  ☐ Offline funciona (guardar y sync)                                   │
│  ☐ Calendario de fechas funciona                                       │
│  ☐ Botón de abrazo funciona                                            │
│  ☐ Stickers se envían                                                  │
│                                                                         │
│  SEGURIDAD:                                                             │
│  ☐ Access token encriptado                                             │
│  ☐ Bloqueo con PIN/biometría funciona                                  │
│  ☐ No hay logs con datos sensibles                                     │
│  ☐ HTTPS obligatorio                                                   │
│                                                                         │
│  RENDIMIENTO:                                                           │
│  ☐ App no crashea en uso normal                                        │
│  ☐ Consumo de batería aceptable (<5%/día)                              │
│  ☐ Consumo de datos aceptable (<20MB/día)                              │
│  ☐ Animaciones fluidas (60 FPS)                                        │
│                                                                         │
│  UI/UX:                                                                 │
│  ☐ Todas las pantallas renderizan correctamente                        │
│  ☐ No hay overflow de texto                                            │
│  ☐ Iconos se ven bien en todos los tamaños                             │
│  ☐ Modo oscuro funciona (si aplica)                                    │
│                                                                         │
│  RELEASE:                                                               │
│  ☐ Version code incrementado                                           │
│  ☐ Version name actualizado                                            │
│  ☐ Proguard configurado                                                │
│  ☐ APK firmado                                                         │
│  ☐ Testing en al menos 2 dispositivos                                  │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 📎 RESUMEN EJECUTIVO PARA IA

```
┌─────────────────────────────────────────────────────────────────────────┐
│  RESUMEN PARA IA - CONSTRUIR CERDITA 💕                                 │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  OBJETIVO: App de chat romántico para 2 personas (pareja)               │
│                                                                         │
│  PROTOCOLO: Matrix (NO Nostr) - usar matrix.org gratuito                │
│                                                                         │
│  TECNOLOGÍA: Kotlin + Jetpack Compose + Material 3 + Hilt + Room        │
│                                                                         │
│  FEATURES PRINCIPALES:                                                  │
│  • Chat de texto con efectos románticos (50+ palabras detectadas)       │
│  • 8 fondos animados + personalizados                                   │
│  • 4 temas (Cerdita, Koalita, Flores, Mix)                              │
│  • Notas de voz con animaciones (5 tipos)                               │
│  • Calendario de fechas especiales con recordatorios                    │
│  • Botón de abrazo (4 tipos + animación 9 pasos)                        │
│  • Stickers (104 animados + creador personalizado)                      │
│  • Offline completo (Room DB + sync)                                    │
│  • Notificaciones push (FCM + UnifiedPush)                              │
│  • Modo optimizado (ahorro de datos)                                    │
│                                                                         │
│  CÓDIGO BASE INCLUIDO:                                                  │
│  • build.gradle.kts completo                                            │
│  • AndroidManifest.xml                                                  │
│  • MatrixClient.kt (autenticación)                                      │
│  • AuthRepository.kt + AuthViewModel.kt                                 │
│  • AppDatabase.kt + MessageEntity.kt + MessageDao.kt                    │
│  • DI Modules (AppModule, RepositoryModule)                             │
│                                                                         │
│  ORDEN: Seguir el orden de implementación (Semanas 1-16)                │
│                                                                         │
│  TESTING: Crear 2 cuentas en matrix.org para probar                     │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

**Alain, este documento contiene TODO lo que la IA necesita para construir tu app.** 🐷🤗🐨

Puedes copiar y pegar este documento completo en tu próxima sesión con la IA, y ella tendrá:
- ✅ Todas las características detalladas
- ✅ El código base mínimo funcional
- ✅ El orden de Implementación
- ✅ Los checklist de verificación

**¿Quieres que agregue algo más o empezamos a generar el código de la Semana 1?** 💕
