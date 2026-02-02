package com.opencrumb.app.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.opencrumb.app.data.RecipeRepository
import com.opencrumb.app.data.model.Recipe
import com.opencrumb.app.data.model.RecipeCategory
import com.opencrumb.app.data.model.RecipeType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class RecipesUiState(
    val recipesByCategory: Map<RecipeCategory, Map<RecipeType, List<Recipe>>> = emptyMap()
)

class RecipeListViewModel(
    private val repository: RecipeRepository,
) : ViewModel() {

    val uiState: StateFlow<RecipesUiState> =
        repository.recipes
            .map { recipes ->
                val recipesByCategory = recipes
                    .groupBy { it.category }
                    .mapValues { (_, recipeList) ->
                        recipeList.groupBy { it.type }
                    }
                RecipesUiState(recipesByCategory = recipesByCategory)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RecipesUiState(),
            )
}

class RecipeListViewModelFactory(
    private val repository: RecipeRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
