package mx.utselva.recetatv.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.utselva.recetatv.core.data.FirestoreService
import mx.utselva.recetatv.mobile.ui.CatalogoScreen
import mx.utselva.recetatv.mobile.ui.ListaComprasScreen
import mx.utselva.recetatv.mobile.ui.ProgresoScreen
import mx.utselva.recetatv.mobile.ui.theme.RecetaTVTheme
class MainActivity : ComponentActivity() {

    private val firestoreService = FirestoreService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecetaTVTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RecetaTVNavHost(firestoreService)
                }
            }
        }
    }
}

private const val RUTA_CATALOGO = "catalogo"
private const val RUTA_PROGRESO = "progreso"
private const val RUTA_LISTA_COMPRAS = "lista_compras"

@Composable
fun RecetaTVNavHost(firestoreService: FirestoreService) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = RUTA_CATALOGO) {

        composable(RUTA_CATALOGO) {
            CatalogoScreen(
                firestoreService = firestoreService,
                onRecetaEnviada = { navController.navigate(RUTA_PROGRESO) },
                onVerListaCompras = { navController.navigate(RUTA_LISTA_COMPRAS) }
            )
        }

        composable(RUTA_PROGRESO) {
            ProgresoScreen(
                firestoreService = firestoreService,
                onVerListaCompras = { navController.navigate(RUTA_LISTA_COMPRAS) },
                onRegresar = { navController.popBackStack() }
            )
        }

        composable(RUTA_LISTA_COMPRAS) {
            ListaComprasScreen(
                firestoreService = firestoreService,
                onRegresar = { navController.popBackStack() }
            )
        }
    }
}
