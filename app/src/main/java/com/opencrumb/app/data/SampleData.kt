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
            Recipe(
                id = 3,
                name = "Rosemary Focaccia",
                description = "Aromatic focaccia topped with fresh rosemary and sea salt.",
                imageRes = 0,
                ingredients =
                    listOf(
                        Ingredient("All-Purpose Flour", 500.0, "g"),
                        Ingredient("Water", 400.0, "g"),
                        Ingredient("Instant Yeast", 2.0, "tsp"),
                        Ingredient("Salt", 10.0, "g"),
                        Ingredient("Olive Oil", 3.0, "tbsp"),
                        Ingredient("Fresh Rosemary", 2.0, "tbsp"),
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
                name = "Pepperoni Pizza",
                description = "Classic pizza loaded with pepperoni and cheese.",
                imageRes = 0,
                ingredients =
                    listOf(
                        Ingredient("Pizza Dough", 300.0, "g"),
                        Ingredient("Tomato Sauce", 100.0, "g"),
                        Ingredient("Mozzarella", 200.0, "g"),
                        Ingredient("Pepperoni", 80.0, "g"),
                    ),
                instructions =
                    listOf(
                        "Preheat oven to 250°C.",
                        "Roll out dough and add sauce.",
                        "Top with cheese and pepperoni.",
                        "Bake for 12-15 minutes.",
                    ),
                category = RecipeCategory.PIZZA,
            ),
            Recipe(
                id = 5,
                name = "Garlic & Herb Focaccia",
                description = "Soft focaccia infused with garlic and Italian herbs.",
                imageRes = 0,
                ingredients =
                    listOf(
                        Ingredient("All-Purpose Flour", 500.0, "g"),
                        Ingredient("Water", 400.0, "g"),
                        Ingredient("Instant Yeast", 2.0, "tsp"),
                        Ingredient("Salt", 10.0, "g"),
                        Ingredient("Olive Oil", 3.0, "tbsp"),
                        Ingredient("Garlic Cloves", 4.0, "cloves"),
                        Ingredient("Italian Herbs", 1.0, "tbsp"),
                    ),
                instructions =
                    listOf(
                        "Mix dough ingredients and let rise overnight.",
                        "Press garlic into dough surface.",
                        "Sprinkle with herbs and oil.",
                        "Bake at 220°C for 25 minutes.",
                    ),
                category = RecipeCategory.FOCACCIA,
            ),
            Recipe(
                id = 6,
                name = "Veggie Supreme Pizza",
                description = "Loaded with fresh vegetables and melted cheese.",
                imageRes = 0,
                ingredients =
                    listOf(
                        Ingredient("Pizza Dough", 300.0, "g"),
                        Ingredient("Tomato Sauce", 100.0, "g"),
                        Ingredient("Mozzarella", 150.0, "g"),
                        Ingredient("Bell Peppers", 50.0, "g"),
                        Ingredient("Mushrooms", 50.0, "g"),
                        Ingredient("Onions", 30.0, "g"),
                        Ingredient("Olives", 30.0, "g"),
                    ),
                instructions =
                    listOf(
                        "Preheat oven to 250°C.",
                        "Roll dough and spread sauce.",
                        "Add cheese and vegetables.",
                        "Bake for 15 minutes until crispy.",
                    ),
                category = RecipeCategory.PIZZA,
            ),
            Recipe(
                id = 7,
                name = "Tomato & Olive Focaccia",
                description = "Mediterranean-style focaccia with cherry tomatoes and olives.",
                imageRes = 0,
                ingredients =
                    listOf(
                        Ingredient("All-Purpose Flour", 500.0, "g"),
                        Ingredient("Water", 400.0, "g"),
                        Ingredient("Instant Yeast", 2.0, "tsp"),
                        Ingredient("Salt", 10.0, "g"),
                        Ingredient("Olive Oil", 3.0, "tbsp"),
                        Ingredient("Cherry Tomatoes", 100.0, "g"),
                        Ingredient("Black Olives", 50.0, "g"),
                    ),
                instructions =
                    listOf(
                        "Prepare dough and let rise.",
                        "Press tomatoes and olives into surface.",
                        "Drizzle with olive oil.",
                        "Bake at 220°C for 25 minutes.",
                    ),
                category = RecipeCategory.FOCACCIA,
            ),
            Recipe(
                id = 8,
                name = "BBQ Chicken Pizza",
                description = "Tangy BBQ sauce with grilled chicken and red onions.",
                imageRes = 0,
                ingredients =
                    listOf(
                        Ingredient("Pizza Dough", 300.0, "g"),
                        Ingredient("BBQ Sauce", 100.0, "g"),
                        Ingredient("Mozzarella", 150.0, "g"),
                        Ingredient("Cooked Chicken", 150.0, "g"),
                        Ingredient("Red Onion", 50.0, "g"),
                    ),
                instructions =
                    listOf(
                        "Preheat oven to 250°C.",
                        "Spread BBQ sauce on dough.",
                        "Add chicken, cheese, and onions.",
                        "Bake for 12-15 minutes.",
                    ),
                category = RecipeCategory.PIZZA,
            ),
        )
}
