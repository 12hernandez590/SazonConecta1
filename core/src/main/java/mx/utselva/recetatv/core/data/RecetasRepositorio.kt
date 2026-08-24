package mx.utselva.recetatv.core.data

import mx.utselva.recetatv.core.model.PasoReceta
import mx.utselva.recetatv.core.model.Receta

/**
 * Fuente de datos de las recetas. Para este proyecto se usan recetas fijas
 * (no requiere backend propio de recetas), tal como se acordó para
 * mantener el alcance del proyecto simple.
 */
object RecetasRepositorio {

    val recetas: List<Receta> = listOf(
        Receta(
            id = "1",
            nombre = "Hot cakes esponjosos",
            descripcionCorta = "Desayuno clásico, listo en 15 minutos",
            tiempoMinutos = 15,
            porciones = 2,
            emojiIcono = "🥞",
            ingredientes = listOf(
                "1 taza de harina",
                "1 cucharada de azúcar",
                "1 cucharadita de polvo para hornear",
                "1 huevo",
                "3/4 taza de leche",
                "1 cucharada de mantequilla derretida"
            ),
            pasos = listOf(
                PasoReceta(1, "Mezcla los ingredientes secos: harina, azúcar y polvo para hornear."),
                PasoReceta(2, "Agrega el huevo, la leche y la mantequilla. Mezcla sin batir de más."),
                PasoReceta(3, "Calienta un sartén a fuego medio y vierte un cucharón de mezcla."),
                PasoReceta(4, "Cocina hasta que salgan burbujas en la superficie.", temporizadorSegundos = 90),
                PasoReceta(5, "Voltea y cocina el otro lado.", temporizadorSegundos = 60),
                PasoReceta(6, "Sirve y agrega tu topping favorito.")
            )
        ),
        Receta(
            id = "2",
            nombre = "Ensalada César",
            descripcionCorta = "Fresca y rápida, ideal para la comida",
            tiempoMinutos = 10,
            porciones = 2,
            emojiIcono = "🥗",
            ingredientes = listOf(
                "1 lechuga romana",
                "1/2 taza de aderezo césar",
                "1/4 taza de queso parmesano",
                "1 taza de crutones",
                "Pechuga de pollo asada (opcional)"
            ),
            pasos = listOf(
                PasoReceta(1, "Lava y corta la lechuga en trozos medianos."),
                PasoReceta(2, "Coloca la lechuga en un tazón grande."),
                PasoReceta(3, "Agrega el aderezo y mezcla bien."),
                PasoReceta(4, "Añade el queso parmesano y los crutones."),
                PasoReceta(5, "Sirve con pollo asado en tiras si gustas.")
            )
        ),
        Receta(
            id = "3",
            nombre = "Pasta al ajillo",
            descripcionCorta = "Sencilla y llena de sabor",
            tiempoMinutos = 20,
            porciones = 3,
            emojiIcono = "🍝",
            ingredientes = listOf(
                "300 g de pasta (espagueti)",
                "4 dientes de ajo",
                "4 cucharadas de aceite de oliva",
                "Chile de árbol al gusto",
                "Perejil picado",
                "Sal al gusto"
            ),
            pasos = listOf(
                PasoReceta(1, "Pon a hervir agua con sal y cocina la pasta.", temporizadorSegundos = 600),
                PasoReceta(2, "Mientras tanto, filetea el ajo en láminas delgadas."),
                PasoReceta(3, "Calienta el aceite y dora el ajo a fuego bajo, sin quemarlo.", temporizadorSegundos = 120),
                PasoReceta(4, "Agrega el chile de árbol al gusto."),
                PasoReceta(5, "Escurre la pasta y mézclala con el aceite de ajo."),
                PasoReceta(6, "Sirve y decora con perejil picado.")
            )
        ),
        Receta(
            id = "4",
            nombre = "Quesadillas de comal",
            descripcionCorta = "El clásico de siempre",
            tiempoMinutos = 12,
            porciones = 2,
            emojiIcono = "🫓",
            ingredientes = listOf(
                "6 tortillas de maíz",
                "200 g de queso Oaxaca",
                "Aceite o mantequilla",
                "Salsa al gusto"
            ),
            pasos = listOf(
                PasoReceta(1, "Calienta el comal a fuego medio."),
                PasoReceta(2, "Coloca la tortilla y agrega el queso."),
                PasoReceta(3, "Dobla la tortilla y cocina de un lado.", temporizadorSegundos = 90),
                PasoReceta(4, "Voltea y cocina hasta que el queso derrita.", temporizadorSegundos = 60),
                PasoReceta(5, "Sirve caliente con salsa al gusto.")
            )
        ),
        Receta(
            id = "5",
            nombre = "Smoothie de fresa y plátano",
            descripcionCorta = "Bebida rápida y nutritiva",
            tiempoMinutos = 5,
            porciones = 1,
            emojiIcono = "🍓",
            ingredientes = listOf(
                "1 taza de fresas",
                "1 plátano",
                "1 taza de leche o yogurt",
                "1 cucharada de miel (opcional)",
                "Hielo al gusto"
            ),
            pasos = listOf(
                PasoReceta(1, "Lava y corta las fresas."),
                PasoReceta(2, "Coloca todos los ingredientes en la licuadora."),
                PasoReceta(3, "Licúa hasta obtener una mezcla uniforme.", temporizadorSegundos = 45),
                PasoReceta(4, "Sirve de inmediato.")
            )
        )
    )

    fun obtenerPorId(id: String): Receta? = recetas.find { it.id == id }
}
