package com.opencrumb.app.ui.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.opencrumb.app.data.RecipeRepository
import com.opencrumb.app.data.model.Recipe
import com.opencrumb.app.data.model.RecipeCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class RecipeListViewModel(
    private val repository: RecipeRepository,
) : ViewModel() {
    val allRecipes: StateFlow<List<Recipe>> =
        repository.recipes
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList(),
            )

    val categories = listOf(RecipeCategory.FOCACCIA, RecipeCategory.PIZZA)

    private val _selectedCategory = MutableStateFlow(categories.first())
    val selectedCategory: StateFlow<RecipeCategory> = _selectedCategory.asStateFlow()

    val filteredRecipes: StateFlow<List<Recipe>> =
        _selectedCategory
            .flatMapLatest { category ->
                repository.getRecipesByCategory(category)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList(),
            )

    fun onCategorySelected(category: RecipeCategory) {
        _selectedCategory.value = category
    }
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
