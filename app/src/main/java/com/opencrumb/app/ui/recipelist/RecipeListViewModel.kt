package com.opencrumb.app.ui.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.opencrumb.app.data.RecipeRepository
import com.opencrumb.app.data.model.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeListViewModel(private val repository: RecipeRepository) : ViewModel() {

    val recipes: Flow<List<Recipe>> = repository.recipes
}

class RecipeListViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
