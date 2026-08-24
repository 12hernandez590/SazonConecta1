package mx.utselva.recetatv.core.model

/**
 * Representa un paso individual dentro de una receta.
 *
 * @param numero número de orden del paso (1, 2, 3...)
 * @param descripcion texto que se muestra en pantalla
 * @param temporizadorSegundos si el paso requiere espera (ej. hornear),
 *        aquí va la duración en segundos. Null si no aplica.
 */
data class PasoReceta(
    val numero: Int,
    val descripcion: String,
    val temporizadorSegundos: Int? = null
)
