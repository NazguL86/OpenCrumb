package com.opencrumb.shared.data

import com.opencrumb.shared.data.model.Guide
import com.opencrumb.shared.data.model.Recipe
import com.opencrumb.shared.data.model.RecipeCategory
import kotlinx.serialization.json.Json

class RecipeRepository {
    private val json = Json { 
        ignoreUnknownKeys = true
        isLenient = true
    }
    
    private var cachedRecipes: List<Recipe>? = null
    private var cachedGuides: List<Guide>? = null
    
    fun getAllRecipes(): List<Recipe> {
        if (cachedRecipes == null) {
            val jsonString = readAssetFile("recipes.json")
            cachedRecipes = json.decodeFromString<List<Recipe>>(jsonString)
        }
        return cachedRecipes ?: emptyList()
    }
    
    fun getRecipesByCategory(category: RecipeCategory): List<Recipe> {
        return getAllRecipes().filter { it.category == category }
    }
    
    fun getRecipeById(id: Int): Recipe? {
        return getAllRecipes().find { it.id == id }
    }
    
    fun getAllGuides(): List<Guide> {
        if (cachedGuides == null) {
            val jsonString = readAssetFile("guides.json")
            cachedGuides = json.decodeFromString<List<Guide>>(jsonString)
        }
        return cachedGuides ?: emptyList()
    }
    
    fun getGuideById(id: Int): Guide? {
        return getAllGuides().find { it.id == id }
    }
}
