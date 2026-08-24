package mx.utselva.recetatv.tv.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.tv.material3.Button
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.OutlinedButton
import androidx.tv.material3.Text
import kotlinx.coroutines.delay
import mx.utselva.recetatv.core.model.Receta

/**
 * Pantalla principal de la app de TV: muestra un paso a la vez en grande.
 * Toda la navegación (anterior / siguiente / temporizador / enviar lista
 * de compras) se controla con el control remoto (D-pad + OK), sin
 * necesidad de tocar el celular.
 */
@Composable
fun ModoCocinaScreen(
    receta: Receta,
    pasoActual: Int,
    onPasoCambiado: (Int) -> Unit,
    onEnviarListaCompras: () -> Unit
) {
    val totalPasos = receta.pasos.size
    val paso = receta.pasos.getOrNull(pasoActual - 1) ?: receta.pasos.first()

    var listaEnviada by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp)
    ) {
        // Encabezado
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(receta.emojiIcono, fontSize = 48.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(receta.nombre, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("Paso $pasoActual de $totalPasos", fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.secondary)
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Texto grande del paso actual
        Text(
            text = paso.descripcion,
            fontSize = 34.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Temporizador, si el paso lo requiere
        paso.temporizadorSegundos?.let { segundosIniciales ->
            Spacer(modifier = Modifier.height(20.dp))
            TemporizadorPaso(segundosIniciales = segundosIniciales)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Controles con D-pad: anterior / siguiente
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { if (pasoActual > 1) onPasoCambiado(pasoActual - 1) },
            ) {
                Text("◀ Paso anterior")
            }

            Button(
                onClick = { if (pasoActual < totalPasos) onPasoCambiado(pasoActual + 1) },
            ) {
                Text(if (pasoActual < totalPasos) "Siguiente ▶" else "Último paso")
            }

            Spacer(modifier = Modifier.weight(1f))

            // Comunicación TV -> móvil: enviar lista de compras
            OutlinedButton(onClick = {
                onEnviarListaCompras()
                listaEnviada = true
            }) {
                Text(if (listaEnviada) "Lista enviada ✅" else "Enviar lista de compras 📝")
            }
        }
    }
}

/** Cuenta regresiva simple para pasos con tiempo de espera (hornear, hervir, etc.). */
@Composable
private fun TemporizadorPaso(segundosIniciales: Int) {
    var segundosRestantes by remember(segundosIniciales) { mutableIntStateOf(segundosIniciales) }
    var corriendo by remember(segundosIniciales) { mutableStateOf(false) }

    LaunchedEffect(corriendo, segundosRestantes) {
        if (corriendo && segundosRestantes > 0) {
            delay(1000)
            segundosRestantes -= 1
        }
    }

    val minutos = segundosRestantes / 60
    val segundos = segundosRestantes % 60

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "⏱ %02d:%02d".format(minutos, segundos),
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = { corriendo = !corriendo }) {
            Text(if (corriendo) "Pausar" else "Iniciar temporizador")
        }
    }
}
