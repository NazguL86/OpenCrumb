package com.opencrumb.shared.data

import com.opencrumb.shared.data.model.Guide
import com.opencrumb.shared.data.model.Recipe
import com.opencrumb.shared.data.model.RecipeCategory
import kotlinx.serialization.json.Json
import kotlinx.coroutines.runBlocking

class RecipeRepository {
    private val json = Json { 
        ignoreUnknownKeys = true
        isLenient = true
    }
    
    private var cachedRecipes: MutableMap<String, List<Recipe>> = mutableMapOf()
    private var cachedGuides: MutableMap<String, List<Guide>> = mutableMapOf()
    private var currentLanguage: String = getDeviceLanguage()
    
    fun setLanguage(languageCode: String) {
        currentLanguage = languageCode
    }
    
    fun getAllRecipes(): List<Recipe> {
        if (cachedRecipes[currentLanguage] == null) {
            val fileName = if (currentLanguage == "en") {
                "recipes.json"
            } else {
                "recipes_$currentLanguage.json"
            }
            val jsonString = runBlocking {
                try {
                    readAssetFile(fileName)
                } catch (e: Exception) {
                    // Fallback to English if translation not found
                    readAssetFile("recipes.json")
                }
            }
            cachedRecipes[currentLanguage] = json.decodeFromString<List<Recipe>>(jsonString)
        }
        return cachedRecipes[currentLanguage] ?: emptyList()
    }
    
    fun getRecipesByCategory(category: RecipeCategory): List<Recipe> {
        return getAllRecipes().filter { it.category == category }
    }
    
    fun getRecipeById(id: Int): Recipe? {
        return getAllRecipes().find { it.id == id }
    }
    
    fun getAllGuides(): List<Guide> {
        if (cachedGuides[currentLanguage] == null) {
            val fileName = if (currentLanguage == "en") {
                "guides.json"
            } else {
                "guides_$currentLanguage.json"
            }
            val jsonString = runBlocking {
                try {
                    readAssetFile(fileName)
                } catch (e: Exception) {
                    // Fallback to English if translation not found
                    readAssetFile("guides.json")
                }
            }
            cachedGuides[currentLanguage] = json.decodeFromString<List<Guide>>(jsonString)
        }
        return cachedGuides[currentLanguage] ?: emptyList()
    }
    
    fun getGuideById(id: Int): Guide? {
        return getAllGuides().find { it.id == id }
    }
}
