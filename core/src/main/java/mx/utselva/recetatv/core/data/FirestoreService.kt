package mx.utselva.recetatv.core.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

/**
 * Punto único de comunicación entre la app móvil y la app de TV.
 * Ambas apps usan esta misma clase (módulo :core) para leer y escribir
 * en los mismos documentos de Firestore, lo que evita duplicar lógica.
 *
 * Estructura en Firestore:
 *
 * coleccion "sesionCocina" / documento "actual"
 *   - recetaId: String            -> qué receta está activa
 *   - pasoActual: Int             -> en qué paso va (lo actualiza la TV)
 *
 * coleccion "listaCompras" / documento "actual"
 *   - ingredientes: List<String>  -> lista enviada desde la TV al celular
 *   - recetaNombre: String
 */
class FirestoreService {

    private val db = FirebaseFirestore.getInstance()

    private val sesionDoc = db.collection("sesionCocina").document("actual")
    private val listaComprasDoc = db.collection("listaCompras").document("actual")

    // ---------- Celular -> TV: elegir receta ----------

    /** El celular llama esto cuando el usuario toca "Enviar a TV". */
    fun enviarRecetaATv(recetaId: String) {
        val datos = mapOf(
            "recetaId" to recetaId,
            "pasoActual" to 1
        )
        sesionDoc.set(datos)
    }

    /** La TV escucha este documento para saber qué receta mostrar. */
    fun escucharSesion(onCambio: (recetaId: String?, pasoActual: Int) -> Unit): ListenerRegistration {
        return sesionDoc.addSnapshotListener { snapshot, _ ->
            val recetaId = snapshot?.getString("recetaId")
            val paso = snapshot?.getLong("pasoActual")?.toInt() ?: 1
            onCambio(recetaId, paso)
        }
    }

    // ---------- TV -> Firestore: avanzar/retroceder pasos ----------

    /** La TV llama esto cuando el usuario mueve el D-pad (siguiente/anterior paso). */
    fun actualizarPasoActual(pasoActual: Int) {
        sesionDoc.update("pasoActual", pasoActual)
    }

    // ---------- TV -> Celular: lista de compras ----------

    /** La TV llama esto al presionar "Enviar lista de compras" con el control remoto. */
    fun enviarListaCompras(recetaNombre: String, ingredientes: List<String>) {
        val datos = mapOf(
            "recetaNombre" to recetaNombre,
            "ingredientes" to ingredientes
        )
        listaComprasDoc.set(datos)
    }

    /** El celular escucha este documento para mostrar el checklist de compras. */
    fun escucharListaCompras(
        onCambio: (recetaNombre: String?, ingredientes: List<String>) -> Unit
    ): ListenerRegistration {
        return listaComprasDoc.addSnapshotListener { snapshot, _ ->
            val nombre = snapshot?.getString("recetaNombre")
            @Suppress("UNCHECKED_CAST")
            val ingredientes = snapshot?.get("ingredientes") as? List<String> ?: emptyList()
            onCambio(nombre, ingredientes)
        }
    }
}
