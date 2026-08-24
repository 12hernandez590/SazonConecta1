package mx.utselva.recetatv.mobile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.utselva.recetatv.core.data.FirestoreService
import mx.utselva.recetatv.core.data.RecetasRepositorio
import mx.utselva.recetatv.core.model.Receta

/**
 * Pantalla 1: catálogo de recetas. El usuario elige una receta y la
 * envía a la app de TV con el botón "Enviar a TV".
 */
@Composable
fun CatalogoScreen(
    firestoreService: FirestoreService,
    onRecetaEnviada: () -> Unit,
    onVerListaCompras: () -> Unit
) {
    var recetaSeleccionada by remember { mutableStateOf<Receta?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {

        Text(
            text = "RecetaTV",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Elige una receta y envíala a tu Smart TV",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(RecetasRepositorio.recetas) { receta ->
                RecetaCard(
                    receta = receta,
                    seleccionada = receta.id == recetaSeleccionada?.id,
                    onClick = { recetaSeleccionada = receta }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                recetaSeleccionada?.let {
                    firestoreService.enviarRecetaATv(it.id)
                    onRecetaEnviada()
                }
            },
            enabled = recetaSeleccionada != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar a TV 📺")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onVerListaCompras,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver lista de compras")
        }
    }
}

@Composable
private fun RecetaCard(receta: Receta, seleccionada: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (seleccionada)
                MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(text = receta.emojiIcono, fontSize = 34.sp)
            Spacer(modifier = Modifier.height(0.dp))
            Column(modifier = Modifier.padding(start = 14.dp)) {
                Text(receta.nombre, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Text(receta.descripcionCorta, fontSize = 13.sp)
                Text("⏱ ${receta.tiempoMinutos} min · 🍽 ${receta.porciones} porciones", fontSize = 12.sp)
            }
        }
    }
}
