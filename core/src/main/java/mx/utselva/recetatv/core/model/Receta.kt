package mx.utselva.recetatv.core.model

/**
 * Representa una receta completa: la información que se muestra en el
 * catálogo de la app móvil y los pasos que se muestran en el Modo Cocina
 * de la app de TV.
 */
data class Receta(
    val id: String,
    val nombre: String,
    val descripcionCorta: String,
    val tiempoMinutos: Int,
    val porciones: Int,
    val emojiIcono: String,
    val ingredientes: List<String>,
    val pasos: List<PasoReceta>
)
