package com.opencrumb.app.data

import com.opencrumb.app.data.local.RecipeDao
import com.opencrumb.app.data.model.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {

    val recipes: Flow<List<Recipe>> = recipeDao.getRecipes()

}
