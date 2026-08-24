package mx.utselva.recetatv.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.tv.material3.Surface
import mx.utselva.recetatv.core.data.FirestoreService
import mx.utselva.recetatv.core.data.RecetasRepositorio
import mx.utselva.recetatv.tv.ui.EsperaScreen
import mx.utselva.recetatv.tv.ui.ModoCocinaScreen
import mx.utselva.recetatv.tv.ui.theme.RecetaTVThemeTV
class MainActivity : ComponentActivity() {

    private val firestoreService = FirestoreService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecetaTVThemeTV {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RecetaTvApp(firestoreService)
                }
            }
        }
    }
}

@Composable
fun RecetaTvApp(firestoreService: FirestoreService) {
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

    if (receta == null) {
        EsperaScreen()
    } else {
        ModoCocinaScreen(
            receta = receta,
            pasoActual = pasoActual,
            onPasoCambiado = { nuevoPaso ->
                pasoActual = nuevoPaso
                firestoreService.actualizarPasoActual(nuevoPaso)
            },
            onEnviarListaCompras = {
                firestoreService.enviarListaCompras(receta.nombre, receta.ingredientes)
            }
        )
    }
}
