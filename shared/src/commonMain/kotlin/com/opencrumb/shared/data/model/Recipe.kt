package com.opencrumb.shared.data.model

import kotlinx.serialization.Serializable

enum class RecipeType {
    DOUGH,
    TOPPING
}

@Serializable
data class Recipe(
    val id: Int = 0,
    val name: String,
    val description: String,
    val imageRes: String = "open_crumb_logo",
    val ingredients: List<Ingredient>,
    val instructions: List<String>,
    val category: RecipeCategory,
    val type: RecipeType,
    val servings: Int = 1,
)
