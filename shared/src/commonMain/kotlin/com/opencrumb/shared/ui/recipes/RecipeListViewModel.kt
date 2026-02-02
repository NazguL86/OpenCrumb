package com.opencrumb.shared.ui.recipes

import com.opencrumb.shared.data.RecipeRepository
import com.opencrumb.shared.data.model.Recipe
import com.opencrumb.shared.data.model.RecipeCategory
import com.opencrumb.shared.data.model.RecipeType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RecipesUiState(
    val recipesByCategory: Map<RecipeCategory, Map<RecipeType, List<Recipe>>> = emptyMap()
)

class RecipeListViewModel(
    private val repository: RecipeRepository = RecipeRepository()
) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    
    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            val recipes = repository.getAllRecipes()
            val recipesByCategory = recipes
                .groupBy { it.category }
                .mapValues { (_, recipeList) ->
                    recipeList.groupBy { it.type }
                }
            _uiState.value = RecipesUiState(recipesByCategory = recipesByCategory)
        }
    }
}
