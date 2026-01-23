package com.opencrumb.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.opencrumb.app.data.model.Recipe
import com.opencrumb.app.data.model.RecipeCategory
import com.opencrumb.app.ui.recipedetail.RecipeDetailScreen
import com.opencrumb.app.ui.recipelist.RecipeListScreen
import com.opencrumb.app.ui.recipelist.RecipeListViewModel
import com.opencrumb.app.ui.recipelist.RecipeListViewModelFactory
import com.opencrumb.app.ui.theme.OpenCrumbTheme

class MainActivity : ComponentActivity() {
    private val viewModel: RecipeListViewModel by viewModels {
        RecipeListViewModelFactory((application as OpenCrumbApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenCrumbTheme {
                val allRecipes by viewModel.allRecipes.collectAsState()
                val filteredRecipes by viewModel.filteredRecipes.collectAsState()
                val selectedCategory by viewModel.selectedCategory.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OpenCrumbApp(
                        modifier = Modifier.padding(innerPadding),
                        allRecipes = allRecipes,
                        filteredRecipes = filteredRecipes,
                        categories = viewModel.categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = viewModel::onCategorySelected,
                    )
                }
            }
        }
    }
}

@Composable
fun OpenCrumbApp(
    modifier: Modifier = Modifier,
    allRecipes: List<Recipe>,
    filteredRecipes: List<Recipe>,
    categories: List<RecipeCategory>,
    selectedCategory: RecipeCategory,
    onCategorySelected: (RecipeCategory) -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "recipe_list",
        modifier = modifier,
    ) {
        composable("recipe_list") {
            RecipeListScreen(
                recipes = filteredRecipes,
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected,
                onRecipeClick = { recipeId ->
                    navController.navigate("recipe_detail/$recipeId")
                },
            )
        }
        composable(
            route = "recipe_detail/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType }),
        ) {
            val recipeId = it.arguments?.getInt("recipeId")
            val recipe = allRecipes.find { it.id == recipeId }
            if (recipe != null) {
                RecipeDetailScreen(recipe = recipe)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OpenCrumbPreview() {
    OpenCrumbTheme {
        OpenCrumbApp(
            allRecipes = emptyList(),
            filteredRecipes = emptyList(),
            categories = listOf(RecipeCategory.PIZZA, RecipeCategory.FOCACCIA),
            selectedCategory = RecipeCategory.PIZZA,
            onCategorySelected = {},
        )
    }
}
