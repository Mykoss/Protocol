# Estado del port MCBE 1.26.40 / protocolo v2168

Este árbol es un **candidato de fuentes** para actualizar el fork `AllayMC/Protocol` desde MCBE 1.26.30 (v1001) a MCBE 1.26.40 (v2168), conservando los códecs NetEase y las versiones anteriores.

## Base utilizada

- Fork base entregado: `1.26.30-R3-SNAPSHOT`.
- Versión del candidato: `1.26.40-R1-SNAPSHOT`.
- Base común de Cloudburst: `93a4c138dc59ace356b9a0f4caf16c97ec0b6b7f`.
- Snapshot upstream v2168: `aec232ec650e25204fcab838b28ebc40a49456fc`.
- Protocolo: `2168`.
- Minecraft: `1.26.40`.

## Integrado

- `Bedrock_v2168` y `BedrockCodecHelper_v2168`.
- Serializadores v2168 para login/movimiento, chunks/subchunks, inventario y crafting, skins/player list, resource packs, StartGame, mapas, sonidos, scoreboard, diagnósticos y transferencia.
- Nuevos modelos de skins, sonidos, diagnósticos, dimensiones y Gatherings.
- Registro de definiciones por identificador textual para items.
- Compatibilidad hacia atrás para las APIs antiguas de persona skins y scoreboard.
- Acción por entrada en `PlayerListPacket`, requerida por v2168.
- IDs explícitos de `BuildPlatform` y corrección de `UNKNOWN=-1`.
- Mapa de categorías de memoria específico de v2168 sin desplazar los IDs de protocolos viejos.
- Directorios NetEase preservados sin cambios.

## Validación realizada en este paquete

- Estructura del códec v2168 y número de protocolo comprobados.
- Imports internos del directorio v2168 comprobados.
- Superconstructores requeridos comprobados.
- Accesores de `record` adaptados a la API del fork.
- Balance léxico de llaves, paréntesis y corchetes en los Java modificados.
- Hashes de los árboles NetEase comparados con el ZIP original.
- Script de verificación validado con `bash -n`.

## Limitación del entorno

No se pudo ejecutar una compilación Gradle completa aquí porque el entorno no puede resolver hosts externos y no tiene descargada la distribución de Gradle ni las dependencias Maven. Por esa razón este paquete **no debe considerarse probado en producción todavía**.

La siguiente validación obligatoria es ejecutar, desde la raíz del repositorio y con Java 21:

```bash
./verify_protocol_1_26_40.sh
```

El script ejecuta controles estáticos y después:

```bash
./gradlew clean test publishToMavenLocal
```

Cuando termine correctamente, el artefacto local esperado será:

```text
org.allaymc.protocol:bedrock-connection:1.26.40-R1-SNAPSHOT
```

Después se actualiza el ZIP de Allay para registrar `Protocol_v2168` y usar este artefacto.

## Fix 1 de compilación

Se corrigieron los imports de `CraftingDataPacket`: las clases `ShapedRecipeData`,
`ShapelessRecipeData`, `MultiRecipeData`, `SmithingTransformRecipeData` y
`SmithingTrimRecipeData` existen en el paquete de recetas, pero no estaban importadas.

## Fix 2 de compilación

A partir de `build-log-3.txt` se añadió una capa de compatibilidad entre el código v2168
portado y la API histórica del fork:

- accesores JavaBean (`getRuntimeId`, `getIdentifier` y getters de varios `record`);
- métodos de helper para `NetworkItemStackDescriptor` con implementación compatible por defecto;
- constructores alternativos para `StructureSettings` y `StructureEditorData`;
- setters de reemplazo para las colecciones finales de `ClientboundMapItemDataPacket`;
- visibilidad protegida para los serializadores que v2168 extiende;
- corrección del uso de `buf` dentro de lambdas de inventario.

Este fix está diseñado para resolver los 39 errores reportados por `:bedrock-codec:compileJava`.
Debe confirmarse ejecutando nuevamente esa tarea en Replit.
