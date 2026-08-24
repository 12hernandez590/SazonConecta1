package mx.utselva.recetatv.mobile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.utselva.recetatv.core.data.FirestoreService

/**
 * Pantalla 3: checklist de ingredientes que la TV envió al celular
 * (comunicación TV -> móvil). El usuario marca lo que ya compró; ese
 * estado se queda solo en el celular, no se vuelve a subir a Firestore.
 */
@Composable
fun ListaComprasScreen(
    firestoreService: FirestoreService,
    onRegresar: () -> Unit
) {
    var recetaNombre by remember { mutableStateOf<String?>(null) }
    var ingredientes by remember { mutableStateOf<List<String>>(emptyList()) }
    val marcados = remember { mutableStateMapOf<String, Boolean>() }

    DisposableEffect(Unit) {
        val listener = firestoreService.escucharListaCompras { nombre, lista ->
            recetaNombre = nombre
            ingredientes = lista
        }
        onDispose { listener.remove() }
    }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text(
            text = "Mi lista de compras 📝",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        if (recetaNombre != null) {
            Text("Enviada desde la TV para: $recetaNombre", fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (ingredientes.isEmpty()) {
            Text("Todavía no tienes ingredientes por comprar.\n\nDesde la app de Smart TV, presiona \"Enviar lista de compras\" durante el Modo Cocina.")
        } else {
            LazyColumn {
                items(ingredientes) { ingrediente ->
                    val marcado = marcados[ingrediente] ?: false
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = marcado,
                            onCheckedChange = { marcados[ingrediente] = it }
                        )
                        Text(ingrediente, fontSize = 15.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(onClick = onRegresar, modifier = Modifier.fillMaxWidth()) {
            Text("Regresar")
        }
    }
}
