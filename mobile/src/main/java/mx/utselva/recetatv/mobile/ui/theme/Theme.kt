package mx.utselva.recetatv.mobile.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NaranjaPrincipal = Color(0xFFE08E45)
private val VerdeAcento = Color(0xFF3E9B5C)
private val FondoOscuro = Color(0xFF1C1B1F)

private val EsquemaRecetaTV = darkColorScheme(
    primary = NaranjaPrincipal,
    secondary = VerdeAcento,
    background = FondoOscuro,
    surface = Color(0xFF2A2830)
)

@Composable
fun RecetaTVTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaRecetaTV,
        content = content
    )
}
