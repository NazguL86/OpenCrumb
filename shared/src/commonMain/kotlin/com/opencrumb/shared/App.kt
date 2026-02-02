package com.opencrumb.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.opencrumb.shared.data.RecipeRepository
import com.opencrumb.shared.ui.calculator.CalculatorScreen
import com.opencrumb.shared.ui.guides.GuideDetailScreen
import com.opencrumb.shared.ui.guides.GuideListScreen
import com.opencrumb.shared.ui.guides.GuideListViewModel
import com.opencrumb.shared.ui.recipes.RecipeDetailScreen
import com.opencrumb.shared.ui.recipes.RecipeListScreen
import com.opencrumb.shared.ui.recipes.RecipeListViewModel
import com.opencrumb.shared.ui.theme.OpenCrumbTheme

@Composable
fun App(onExternalUrl: (String) -> Unit = {}) {
    OpenCrumbTheme {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.Recipes) }
        var selectedRecipeId by remember { mutableIntStateOf(0) }
        var selectedGuideId by remember { mutableIntStateOf(0) }
        
        val repository = remember { RecipeRepository() }
        val recipeViewModel = remember { RecipeListViewModel(repository) }
        val guideViewModel = remember { GuideListViewModel(repository) }
        
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Text("📖") },
                        label = { Text("Recipes") },
                        selected = currentScreen is Screen.Recipes || currentScreen is Screen.RecipeDetail,
                        onClick = { currentScreen = Screen.Recipes }
                    )
                    NavigationBarItem(
                        icon = { Text("🧮") },
                        label = { Text("Calculator") },
                        selected = currentScreen is Screen.Calculator,
                        onClick = { currentScreen = Screen.Calculator }
                    )
                    NavigationBarItem(
                        icon = { Text("📚") },
                        label = { Text("Guides") },
                        selected = currentScreen is Screen.Guides || currentScreen is Screen.GuideDetail,
                        onClick = { currentScreen = Screen.Guides }
                    )
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when (val screen = currentScreen) {
                    is Screen.Recipes -> {
                        val uiState by recipeViewModel.uiState.collectAsState()
                        RecipeListScreen(
                            uiState = uiState,
                            onRecipeClick = { recipeId ->
                                selectedRecipeId = recipeId
                                currentScreen = Screen.RecipeDetail
                            }
                        )
                    }
                    is Screen.RecipeDetail -> {
                        val recipe = repository.getRecipeById(selectedRecipeId)
                        recipe?.let {
                            RecipeDetailScreen(
                                recipe = it,
                                onBack = { currentScreen = Screen.Recipes }
                            )
                        }
                    }
                    is Screen.Calculator -> {
                        CalculatorScreen()
                    }
                    is Screen.Guides -> {
                        val uiState by guideViewModel.uiState.collectAsState()
                        GuideListScreen(
                            uiState = uiState,
                            onGuideClick = { guideId ->
                                selectedGuideId = guideId
                                currentScreen = Screen.GuideDetail
                            },
                            onExternalUrlClick = onExternalUrl
                        )
                    }
                    is Screen.GuideDetail -> {
                        val guide = repository.getGuideById(selectedGuideId)
                        guide?.let {
                            GuideDetailScreen(
                                guide = it,
                                onBack = { currentScreen = Screen.Guides }
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen {
    object Recipes : Screen()
    object RecipeDetail : Screen()
    object Calculator : Screen()
    object Guides : Screen()
    object GuideDetail : Screen()
}

