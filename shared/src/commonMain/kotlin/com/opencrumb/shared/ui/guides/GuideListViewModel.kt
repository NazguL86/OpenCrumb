package com.opencrumb.shared.ui.guides

import com.opencrumb.shared.data.RecipeRepository
import com.opencrumb.shared.data.model.Guide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GuideUiState(
    val guides: List<Guide> = emptyList(),
    val isLoading: Boolean = true
)

class GuideListViewModel(
    private val repository: RecipeRepository = RecipeRepository()
) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    
    private val _uiState = MutableStateFlow(GuideUiState())
    val uiState: StateFlow<GuideUiState> = _uiState.asStateFlow()

    init {
        loadGuides()
    }

    private fun loadGuides() {
        viewModelScope.launch {
            try {
                val guides = repository.getAllGuides()
                _uiState.value = GuideUiState(guides = guides, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = GuideUiState(guides = emptyList(), isLoading = false)
            }
        }
    }
}
