# 📦 GUÍA DE ARTIFACTS - CERDITA 💕

**Workflow:** Strict CI - Quality Gate  
**Archivo:** `.github/workflows/strict-ci.yml`

---

## 🎯 ARTIFACTS GENERADOS

Cada vez que se ejecuta el workflow, se generan **7 artifacts** numerados:

| # | Artifact | Contenido | Retención |
|---|----------|-----------|-----------|
| 00 | `00-complete-build-logs` | **TODOS los logs de compilación** | 30 días |
| 01 | `01-kotlin-compile-logs` | Logs de compilación Kotlin | 14 días |
| 02 | `02-lint-logs-and-reports` | Reports de Lint (HTML, XML, SARIF) | 14 días |
| 03 | `03-apk-debug` | **APK Debug para testing** | 14 días |
| 04 | `04-debug-build-logs` | Logs del build debug | 14 días |
| 05 | `05-apk-release` | **APK Release optimizado** | 14 días |
| 06 | `06-release-build-logs` | Logs del build release | 14 días |

---

## 📥 CÓMO DESCARGAR ARTIFACTS

### Paso 1: Ir a GitHub Actions
```
https://github.com/Alain314159/solid-computing-machine/actions
```

### Paso 2: Seleccionar el workflow run
- Click en **"Strict CI - Quality Gate"**
- Click en el run más reciente (o el que necesites)

### Paso 3: Descargar artifacts
Al final de la página, verás la sección **"Artifacts"**:

```
┌─────────────────────────────────────────┐
│  Artifacts                              │
├─────────────────────────────────────────┤
│  📦 00-complete-build-logs (50 MB)     │
│  📦 01-kotlin-compile-logs (5 MB)      │
│  📦 02-lint-logs-and-reports (2 MB)    │
│  📦 03-apk-debug (45 MB) ← DEBUG APK   │
│  📦 04-debug-build-logs (3 MB)         │
│  📦 05-apk-release (42 MB) ← RELEASE APK│
│  📦 06-release-build-logs (3 MB)       │
└─────────────────────────────────────────┘
```

- Click en el artifact que quieras descargar
- Se descarga como `.zip`
- Extrae el archivo

---

## 🔍 QUÉ CONTIENE CADA ARTIFACT

### 00-complete-build-logs ⭐ RECOMENDADO
**Contiene TODOS los logs de toda la compilación:**
```
00-complete-build-logs/
├── kotlin-compile.log      # Logs de compilación Kotlin
├── lint.log                # Logs de lint
├── debug-build.log         # Logs del build debug
├── release-build.log       # Logs del build release
└── build/
    └── reports/            # Reports generados
```

**Úsalo para:**
- Debuggear errores de compilación
- Ver el log completo de todo el build
- Compartir con el equipo cuando algo falla

---

### 01-kotlin-compile-logs
**Contiene:**
```
01-kotlin-compile-logs/
├── build/logs/kotlin-compile.log
└── build/reports/
    └── kotlin/             # Reports de compilación
```

**Úsalo para:**
- Errores específicos de Kotlin
- Verificar que todo compiló bien
- Análisis de código

---

### 02-lint-logs-and-reports
**Contiene:**
```
02-lint-logs-and-reports/
├── lint.log                           # Log completo de lint
├── lint-results-debug.html            # Reporte HTML (ábrel en browser)
├── lint-results-debug.xml             # Reporte XML
└── lint-results-debug.sarif           # Reporte SARIF (para VS Code)
```

**Úsalo para:**
- Ver errores de lint
- Abrir `lint-results-debug.html` en tu navegador
- Ver advertencias de código

---

### 03-apk-debug ⭐ MÁS IMPORTANTE
**Contiene:**
```
03-apk-debug/
└── app-debug.apk          # APK Debug (45-50 MB)
```

**Úsalo para:**
- ✅ **Instalar en tu dispositivo para testing**
- ✅ **Probar la app en desarrollo**
- Logging habilitado
- Debugging activo

**Cómo instalar:**
1. Descarga el artifact
2. Extrae el ZIP
3. Copia `app-debug.apk` a tu dispositivo
4. Instala (habilita "Orígenes desconocidos")
5. Abre la app

---

### 04-debug-build-logs
**Contiene:**
```
04-debug-build-logs/
├── debug-build.log                    # Log completo del build debug
└── output-metadata.json               # Metadata del build
```

**Úsalo para:**
- Debuggear errores del build debug
- Ver tiempo de compilación
- Verificar configuración

---

### 05-apk-release ⭐ PRODUCCIÓN
**Contiene:**
```
05-apk-release/
└── app-release-unsigned.apk   # APK Release (42-48 MB)
```

**Úsalo para:**
- ✅ **Instalar en dispositivo para uso normal**
- ✅ **Compartir con tu pareja**
- Optimizado y sin logging
- Tamaño reducido

**Cómo instalar:**
1. Descarga el artifact
2. Extrae el ZIP
3. Copia `app-release-unsigned.apk` a tu dispositivo
4. Instala
5. Abre la app

---

### 06-release-build-logs
**Contiene:**
```
06-release-build-logs/
├── release-build.log                  # Log completo del build release
└── output-metadata.json               # Metadata del build
```

**Úsalo para:**
- Debuggear errores del build release
- Verificar optimizaciones
- Análisis de tamaño

---

## 🎯 FLUJO RECOMENDADO

### Para Testing/Desarrollo:
```
1. Ve a Actions → Strict CI - Quality Gate
2. Click en el run más reciente
3. Descarga: 03-apk-debug
4. Instala en tu dispositivo
5. Prueba la app
```

### Para Producción/Uso:
```
1. Ve a Actions → Strict CI - Quality Gate
2. Click en el run más reciente
3. Descarga: 05-apk-release
4. Instala en tu dispositivo
5. Usa la app normalmente
```

### Cuando Algo Falla:
```
1. Ve a Actions → Strict CI - Quality Gate
2. Click en el run fallido
3. Descarga: 00-complete-build-logs
4. Revisa los logs para ver el error
5. Comparte los logs si necesitas ayuda
```

---

## 📊 EJEMPLO DE USO

### Escenario: La compilación falló

**Paso 1:** Ir al workflow run fallido
```
Actions → Strict CI - Quality Gate → Run #123 (Failed)
```

**Paso 2:** Ver el resumen
Al final del run, verás algo como:
```
❌ Quality Gate FAILED

Download Logs:
- 00-complete-build-logs: Todos los logs
- 01-kotlin-compile-logs: Kotlin
- 02-lint-logs-and-reports: Lint
```

**Paso 3:** Descargar logs
- Click en `00-complete-build-logs`
- Descarga el ZIP
- Extrae

**Paso 4:** Revisar logs
```bash
# En tu computadora
cd 00-complete-build-logs
cat kotlin-compile.log | grep "ERROR"
```

**Paso 5:** Identificar el error
```
ERROR: Unresolved reference: MatrixClient
  en app/src/main/java/com/cerdita/app/data/repository/MessageRepository.kt:15
```

**Paso 6:** Corregir y hacer push
```bash
# Corrige el código
git add .
git commit -m "fix: Corregir import de MatrixClient"
git push
```

**Paso 7:** Verificar nuevo build
- El workflow se ejecuta automáticamente
- Si pasa, descarga el APK
- Si falla, repite el proceso

---

## 🔥 TIPS Y TRICKS

### 1. Descarga rápida del APK
```
Solo necesitas: 03-apk-debug o 05-apk-release
No descargues los logs a menos que algo falle
```

### 2. Logs solo cuando fallen
```
Si el build pasa ✅ → Solo descarga el APK
Si el build falla ❌ → Descarga 00-complete-build-logs
```

### 3. Retención de artifacts
```
- Los artifacts expiran después de 14-30 días
- Si necesitas un APK viejo, descárgalo antes
- Los releases de GitHub no expiran
```

### 4. Compartir con tu pareja
```
1. Ve al workflow run más reciente
2. Descarga 05-apk-release
3. Envía el APK por WhatsApp/Telegram
4. Dile que instale
```

### 5. Ver logs sin descargar
```
1. Click en el job (ej: "Kotlin Compilation")
2. Expande las flechas de los pasos
3. Lee los logs en el navegador
4. Solo descarga si necesitas analizar en detalle
```

---

## 📱 INSTALACIÓN DEL APK

### En tu dispositivo:

**Paso 1:** Transferir APK
```
- Por USB desde tu computadora
- Por Google Drive/Dropbox
- Por WhatsApp/Telegram (a ti mismo)
```

**Paso 2:** Habilitar instalación
```
Ajustes → Seguridad → Orígenes desconocidos → ACTIVAR
```

**Paso 3:** Instalar
```
- Abre el administrador de archivos
- Busca el APK
- Click en "Instalar"
```

**Paso 4:** Abrir
```
- Abre la app "Cerdita 💕"
- Inicia sesión con Matrix
- Configura Ntfy
```

---

## 🎉 RESUMEN RÁPIDO

| Quiero... | Descarga... |
|-----------|-------------|
| **Probar la app** | `03-apk-debug` |
| **Usar la app** | `05-apk-release` |
| **Ver por qué falló** | `00-complete-build-logs` |
| **Ver errores de lint** | `02-lint-logs-and-reports` |
| **Todo junto** | Todos los artifacts |

---

## 🔗 LINKS ÚTILES

- **Repo:** https://github.com/Alain314159/solid-computing-machine
- **Actions:** https://github.com/Alain314159/solid-computing-machine/actions
- **Workflow:** `.github/workflows/strict-ci.yml`

---

**¡LISTO! 🐷🤗🐨💕**

Los artifacts hacen fácil:
- ✅ Obtener el APK para testing
- ✅ Obtener el APK para producción
- ✅ Debuggear errores
- ✅ Compartir con tu pareja
