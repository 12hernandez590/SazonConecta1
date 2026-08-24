package mx.utselva.recetatv.tv.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

/**
 * Pantalla que se muestra en la TV mientras no hay ninguna receta activa.
 * En cuanto el celular envía una receta, MainActivity cambia automáticamente
 * a ModoCocinaScreen (escucha en tiempo real vía Firestore).
 */
@Composable
fun EsperaScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text(
            text = "🍳",
            fontSize = 90.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "RecetaTV",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Selecciona una receta desde tu celular para comenzar",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
