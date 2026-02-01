package com.opencrumb.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.opencrumb.app.data.model.Recipe
import com.opencrumb.app.data.model.RecipeType
import com.opencrumb.app.ui.calculator.CalculatorScreen
import com.opencrumb.app.ui.recipedetail.RecipeDetailScreen
import com.opencrumb.app.ui.recipelist.RecipeListScreen
import com.opencrumb.app.ui.recipelist.RecipeListViewModel
import com.opencrumb.app.ui.recipelist.RecipeListViewModelFactory
import com.opencrumb.app.ui.theme.OpenCrumbTheme

// 1. Define Navigation Routes
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Recipes : Screen("recipes", "Recipes", Icons.AutoMirrored.Filled.MenuBook)
    object Calculator : Screen("calculator", "Calculator", Icons.Default.Calculate)
}

val items = listOf(
    Screen.Recipes,
    Screen.Calculator,
)

class MainActivity : ComponentActivity() {
    private val viewModel: RecipeListViewModel by viewModels {
        RecipeListViewModelFactory((application as OpenCrumbApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenCrumbTheme {
                // Hoist the NavController to the top level
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        AppBottomBar(
                            navController = navController,
                            onNavigate = { screen ->
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            })
                    }
                ) { innerPadding ->
                    val uiState by viewModel.uiState.collectAsState()

                    // Memoize the flattened list of recipes to avoid recalculating on every recomposition
                    val allRecipes: List<Recipe> = remember(uiState) {
                        uiState.recipesByCategory.values
                            .flatMap { it.values }
                            .flatten()
                    }

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Recipes.route, // Start at the recipes screen
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                    ) {
                        // Recipes List Screen
                        composable(Screen.Recipes.route) {
                            RecipeListScreen(
                                uiState = uiState,
                                onRecipeClick = { recipeId ->
                                    navController.navigate("recipe_detail/$recipeId")
                                },
                                modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                            )
                        }

                        // Recipe Detail Screen
                        composable(
                            route = "recipe_detail/{recipeId}",
                            arguments = listOf(navArgument("recipeId") { type = NavType.IntType }),
                        ) { backStackEntry ->
                            val recipeId = backStackEntry.arguments?.getInt("recipeId")
                            // Find the recipe from the memoized list
                            val recipe = allRecipes.find { it.id == recipeId }
                            if (recipe != null) {
                                val toppingSuggestions = remember(uiState, recipe) {
                                    uiState.recipesByCategory[recipe.category]?.get(RecipeType.TOPPING) ?: emptyList()
                                }
                                RecipeDetailScreen(
                                    recipe = recipe,
                                    onBack = { navController.popBackStack() },
                                    toppingSuggestions = toppingSuggestions,
                                    bottomContentPadding = innerPadding.calculateBottomPadding()
                                )
                            }
                        }

                        // Calculator Screen
                        composable(Screen.Calculator.route) {
                            CalculatorScreen(modifier = Modifier.padding(top = innerPadding.calculateTopPadding()))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppBottomBar(
    navController: NavController,
    onNavigate: (Screen) -> Unit
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.label) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = { onNavigate(screen) },
            )
        }
    }
}
