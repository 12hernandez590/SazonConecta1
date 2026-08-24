package mx.utselva.recetatv.tv.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.darkColorScheme

private val NaranjaPrincipal = Color(0xFFE08E45)
private val VerdeAcento = Color(0xFF3E9B5C)
private val FondoOscuro = Color(0xFF141318)

private val EsquemaTV = darkColorScheme(
    primary = NaranjaPrincipal,
    secondary = VerdeAcento,
    background = FondoOscuro,
    surface = Color(0xFF211F26)
)

@Composable
fun RecetaTVThemeTV(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaTV,
        content = content
    )
}
