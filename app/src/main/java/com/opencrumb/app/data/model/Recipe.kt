package com.opencrumb.app.data.model

import androidx.annotation.DrawableRes

data class Recipe(
    val id: String,
    val name: String,
    val description: String,
    @DrawableRes val imageRes: Int,
    val ingredients: List<Ingredient>,
    val instructions: List<String>
)
