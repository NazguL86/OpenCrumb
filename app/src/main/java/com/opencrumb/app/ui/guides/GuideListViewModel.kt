package com.opencrumb.app.ui.guides

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.opencrumb.app.data.model.Guide
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GuideUiState(
    val guides: List<Guide> = emptyList(),
    val isLoading: Boolean = true
)

class GuideListViewModel(private val context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(GuideUiState())
    val uiState: StateFlow<GuideUiState> = _uiState.asStateFlow()

    init {
        loadGuides()
    }

    private fun loadGuides() {
        viewModelScope.launch {
            try {
                val json = context.assets.open("guides.json").bufferedReader().use { it.readText() }
                val type = object : TypeToken<List<Guide>>() {}.type
                val guides: List<Guide> = Gson().fromJson(json, type)
                _uiState.value = GuideUiState(guides = guides, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = GuideUiState(guides = emptyList(), isLoading = false)
            }
        }
    }
}

class GuideListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GuideListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GuideListViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
