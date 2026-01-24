package com.opencrumb.app.data

import com.opencrumb.app.R
import com.opencrumb.app.data.model.Ingredient
import com.opencrumb.app.data.model.Recipe
import com.opencrumb.app.data.model.RecipeCategory

object SampleData {
    val recipes =
        listOf(
            Recipe(
                id = 1,
                name = "Focaccia Genovese",
                description = "A delicious and easy-to-make focaccia, perfect for beginners.",
                imageRes = R.drawable.focaccia_genovese,
                servings = 2,
                ingredients =
                    listOf(
                        Ingredient("Flour", 550.0, "g"),
                        Ingredient("Malt", 15.0, "g"),
                        Ingredient("Water", 330.0, "g"),
                        Ingredient("Instant Yeast", 6.0, "g"),
                        Ingredient("Salt", 12.0, "g"),
                        Ingredient("Olive Oil", 25.0, "g"),
                    ),
                instructions =
                    listOf(
                        "Mix all ingredients in a bowl.",
                        "Let it rise for 12-18 hours.",
                        "Shape and bake at 220°C for 25 minutes.",
                    ),
                category = RecipeCategory.FOCACCIA,
            ),
            Recipe(
                id = 2,
                name = "24h Fermentation Pizza",
                description = "The traditional Italian pizza with tomato, mozzarella, and basil.",
                imageRes = R.drawable.pizza,
                servings = 3,
                ingredients =
                    listOf(
                        Ingredient("Flour", 424.0, "g"),
                        Ingredient("Water", 295.0, "g"),
                        Ingredient("Salt", 11.0, "g"),
                        Ingredient("Instant yeast", 1.5, "g"),
                    ),
                instructions =
                    listOf(
                        "24h fermentation",
                    ),
                category = RecipeCategory.PIZZA,
            ),
            Recipe(
                id = 3,
                name = "Focaccia Papi",
                description = "Aromatic focaccia topped with fresh rosemary and sea salt.",
                imageRes = R.drawable.focaccia,
                servings = 1,
                ingredients =
                    listOf(
                        Ingredient("Flour", 325.0, "g"),
                        Ingredient("Water", 260.0, "g"),
                        Ingredient("Instant Yeast", 1.17, "g"),
                        Ingredient("Salt", 6.5, "g"),
                        Ingredient("Olive Oil", 8.45, "g"),
                    ),
                instructions =
                    listOf(
                        "Mix flour, water, yeast, and salt.",
                        "Let rise for 12 hours.",
                        "Dimple dough, add rosemary and oil.",
                        "Bake at 220°C for 25 minutes.",
                    ),
                category = RecipeCategory.FOCACCIA,
            ),
            Recipe(
                id = 4,
                name = "Focaccia Pizza Piatto",
                description = "Soft focaccia pizza style.",
                imageRes = R.drawable.focaccia_pizza,
                servings = 2,
                ingredients =
                    listOf(
                        Ingredient("Flour", 700.0, "g"),
                        Ingredient("Water", 500.0, "g"),
                        Ingredient("Instant Yeast", 2.0, "tsp"),
                        Ingredient("Salt", 18.0, "g"),
                        Ingredient("Instant Yeast", 2.5, "g"),
                    ),
                instructions =
                    listOf(
                        "* Open up the dough and fold it inside" +
                            "* 2 piega and wait 10m" +
                            "* 2 piega and wait 2 hours" +
                            "* cut in 2 pieces (600gr) and close them up wait 3 hours" +
                            "* put them on the tray and wait 1 hour" +
                            "* oven 300 for 10 static or 250 for 15m" +
                            "* add mozzarella and 3m more ventilato",
                    ),
                category = RecipeCategory.FOCACCIA,
            ),
        )
}
