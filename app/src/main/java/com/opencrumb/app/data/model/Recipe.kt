package com.opencrumb.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.opencrumb.app.data.local.Converters

@Entity(tableName = "recipes")
@TypeConverters(Converters::class)
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val imageRes: String = "open_crumb_logo",
    val ingredients: List<Ingredient>,
    val instructions: List<String>,
    val category: RecipeCategory,
    val servings: Int = 1,
)
