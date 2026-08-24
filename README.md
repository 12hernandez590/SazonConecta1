# RecetaTV — Ejercicio Final Mobile y Smart TV

Proyecto para la materia **Desarrollo para Dispositivos Inteligentes**, Unidad III.

## Descripción de la solución
App móvil + app de Smart TV (ambas con **Jetpack Compose** / **Compose for TV**) que
permiten elegir una receta desde el celular, seguirla paso a paso en la TV usando el
control remoto, y recibir de vuelta en el celular la lista de ingredientes como checklist
para el súper. La comunicación entre ambas apps es **bidireccional** vía **Firebase Firestore**.

### Flujo
1. **Celular → TV**: el usuario elige una receta en la app móvil y la envía a la TV.
2. **TV**: entra en "Modo Cocina" y muestra los pasos uno a uno, controlados con el
   D-pad del control remoto (anterior / siguiente), incluyendo temporizador en los pasos
   que lo requieren.
3. **TV → Celular**: desde la TV, el usuario presiona "Enviar lista de compras" y esos
   ingredientes llegan al celular como un checklist.
4. **Celular**: muestra el progreso de la receta (informativo) y el checklist de compras.

## Estructura del proyecto (módulos)
- **`core`**: módulo compartido — modelos de datos (`Receta`, `PasoReceta`), recetas
  de ejemplo y el servicio de Firestore (`FirestoreService`) usado por ambas apps.
- **`mobile`**: app de celular (Catálogo, Progreso, Lista de compras).
- **`tv`**: app de Smart TV / Android TV (Espera, Modo Cocina).

## Configuraciones adicionales necesarias

Este proyecto usa **Firebase Firestore** para la comunicación entre apps. Para poder
ejecutarlo necesitas:

1. Crear un proyecto en [Firebase Console](https://console.firebase.google.com/).
2. Registrar **dos apps Android** dentro del mismo proyecto de Firebase:
   - `mx.utselva.recetatv.mobile`
   - `mx.utselva.recetatv.tv`
3. Descargar el archivo `google-services.json` de **cada** app y colocarlo en:
   - `mobile/google-services.json`
   - `tv/google-services.json`

   (Ese archivo no viene incluido en este zip porque es específico de cada cuenta de
   Firebase; sin él el proyecto no compilará).
4. En Firebase Console, activar **Firestore Database** (modo de prueba está bien para
   fines académicos).
5. Ambos dispositivos (celular y TV/emulador) deben tener conexión a internet y usar
   el **mismo proyecto de Firebase** para poder verse entre sí.

## Cómo abrir el proyecto
1. Abre Android Studio → **Open** → selecciona la carpeta `RecetaTV`.
2. Deja que Gradle sincronice (puede pedir descargar el wrapper de Gradle si no
   está disponible localmente; acepta la sugerencia de Android Studio).
3. Agrega los `google-services.json` como se indicó arriba.
4. Selecciona la configuración `mobile` para correr en tu celular/emulador de teléfono,
   y la configuración `tv` para correr en un emulador de Android TV.

## Icono personalizado
Ambas apps usan un ícono adaptativo propio (silueta de sartén) definido en
`res/drawable/ic_launcher_foreground.xml` y `ic_launcher_background.xml` de cada módulo.

## Control de versiones
Recuerda inicializar git y hacer commits siguiendo lo pedido en la rúbrica:
```
git init
git add .
git commit -m "Commit inicial: estructura del proyecto RecetaTV"
```
Y continuar con un commit por cada día de desarrollo indicando la funcionalidad lograda.
