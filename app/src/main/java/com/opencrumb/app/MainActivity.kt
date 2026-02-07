package com.opencrumb.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.MenuBook
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.opencrumb.shared.data.RecipeRepository
import com.opencrumb.shared.data.initAndroidContext
import com.opencrumb.shared.ui.calculator.CalculatorScreen
import com.opencrumb.shared.ui.guides.GuideDetailScreen
import com.opencrumb.shared.ui.guides.GuideListScreen
import com.opencrumb.shared.ui.guides.GuideListViewModel
import com.opencrumb.shared.ui.recipes.RecipeDetailScreen
import com.opencrumb.shared.ui.recipes.RecipeListScreen
import com.opencrumb.shared.ui.recipes.RecipeListViewModel
import com.opencrumb.shared.ui.theme.OpenCrumbTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initAndroidContext(applicationContext)

        setContent {
            OpenCrumbTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val repository = remember { RecipeRepository() }
    val recipeViewModel = remember { RecipeListViewModel(repository) }
    val guideViewModel = remember { GuideListViewModel(repository) }

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null) },
                    label = { Text(context.getString(R.string.nav_recipes)) },
                    selected = currentRoute == "recipes",
                    onClick = {
                        navController.navigate("recipes") {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Calculate, contentDescription = null) },
                    label = { Text(context.getString(R.string.nav_calculator)) },
                    selected = currentRoute == "calculator",
                    onClick = {
                        navController.navigate("calculator") {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = null) },
                    label = { Text(context.getString(R.string.nav_guides)) },
                    selected = currentRoute == "guides",
                    onClick = {
                        navController.navigate("guides") {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "recipes",
            modifier = Modifier.padding(paddingValues),
        ) {
            composable("recipes") {
                val uiState by recipeViewModel.uiState.collectAsState()
                RecipeListScreen(
                    uiState = uiState,
                    onRecipeClick = { recipeId ->
                        navController.navigate("recipe/$recipeId")
                    },
                )
            }

            composable(
                route = "recipe/{recipeId}",
                arguments = listOf(navArgument("recipeId") { type = NavType.IntType }),
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
                val recipe = repository.getRecipeById(recipeId)
                recipe?.let {
                    RecipeDetailScreen(
                        recipe = it,
                        onBack = { navController.popBackStack() },
                    )
                }
            }

            composable("calculator") {
                CalculatorScreen()
            }

            composable("guides") {
                val uiState by guideViewModel.uiState.collectAsState()
                GuideListScreen(
                    uiState = uiState,
                    onGuideClick = { guideId ->
                        navController.navigate("guide/$guideId")
                    },
                    onExternalUrlClick = { url ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    },
                )
            }

            composable(
                route = "guide/{guideId}",
                arguments = listOf(navArgument("guideId") { type = NavType.IntType }),
            ) { backStackEntry ->
                val guideId = backStackEntry.arguments?.getInt("guideId") ?: 0
                val guide = repository.getGuideById(guideId)
                guide?.let {
                    GuideDetailScreen(
                        guide = it,
                        onBack = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}
