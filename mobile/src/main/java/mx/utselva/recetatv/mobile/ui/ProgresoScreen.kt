package mx.utselva.recetatv.mobile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.utselva.recetatv.core.data.FirestoreService
import mx.utselva.recetatv.core.data.RecetasRepositorio

/**
 * Pantalla 2: muestra en qué paso va la receta en la TV. Es informativa;
 * el control real de los pasos se hace desde el control remoto de la TV.
 */
@Composable
fun ProgresoScreen(
    firestoreService: FirestoreService,
    onVerListaCompras: () -> Unit,
    onRegresar: () -> Unit
) {
    var recetaId by remember { mutableStateOf<String?>(null) }
    var pasoActual by remember { mutableIntStateOf(1) }

    DisposableEffect(Unit) {
        val listener = firestoreService.escucharSesion { id, paso ->
            recetaId = id
            pasoActual = paso
        }
        onDispose { listener.remove() }
    }

    val receta = recetaId?.let { RecetasRepositorio.obtenerPorId(it) }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cocinando en la TV",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (receta == null) {
            Text("Esperando datos de la TV...")
        } else {
            Text(receta.emojiIcono, fontSize = 60.sp)
            Text(receta.nombre, fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            val totalPasos = receta.pasos.size
            val progreso = pasoActual.toFloat() / totalPasos.toFloat()

            Text("Paso $pasoActual de $totalPasos")
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progreso.coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            val pasoTexto = receta.pasos.getOrNull(pasoActual - 1)?.descripcion.orEmpty()
            Text(pasoTexto, fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(onClick = onVerListaCompras, modifier = Modifier.fillMaxWidth()) {
            Text("Ver lista de compras 🛒")
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = onRegresar, modifier = Modifier.fillMaxWidth()) {
            Text("Elegir otra receta")
        }
    }
}
