package com.opencrumb.app.data

import com.opencrumb.app.data.model.Ingredient
import com.opencrumb.app.data.model.Recipe
import com.opencrumb.app.data.model.RecipeCategory

object SampleData {
    val recipes =
        listOf(
            Recipe(
                id = 1,
                name = "Simple Focaccia",
                description = "A delicious and easy-to-make focaccia, perfect for beginners.",
                imageRes = 0,
                ingredients =
                    listOf(
                        Ingredient("All-Purpose Flour", 500.0, "g"),
                        Ingredient("Water", 400.0, "g"),
                        Ingredient("Instant Yeast", 2.0, "tsp"),
                        Ingredient("Salt", 10.0, "g"),
                        Ingredient("Olive Oil", 2.0, "tbsp"),
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
                name = "Classic Margherita Pizza",
                description = "The traditional Italian pizza with tomato, mozzarella, and basil.",
                imageRes = 0,
                ingredients =
                    listOf(
                        Ingredient("Pizza Dough", 300.0, "g"),
                        Ingredient("Tomato Sauce", 100.0, "g"),
                        Ingredient("Fresh Mozzarella", 150.0, "g"),
                        Ingredient("Basil Leaves", 10.0, "g"),
                        Ingredient("Olive Oil", 1.0, "tbsp"),
                    ),
                instructions =
                    listOf(
                        "Preheat oven to 250°C.",
                        "Roll out the pizza dough.",
                        "Add sauce, mozzarella, and basil.",
                        "Bake for 12-15 minutes until golden.",
                    ),
                category = RecipeCategory.PIZZA,
            ),
        )
}
