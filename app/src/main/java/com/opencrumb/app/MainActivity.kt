package com.opencrumb.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.opencrumb.app.data.SampleData
import com.opencrumb.app.ui.recipelist.RecipeListScreen
import com.opencrumb.app.ui.theme.OpenCrumbTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenCrumbTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OpenCrumbApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun OpenCrumbApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "recipe_list",
        modifier = modifier,
    ) {
        composable("recipe_list") {
            RecipeListScreen(
                recipes = SampleData.recipes,
                onRecipeClick = { recipeId ->
                    Toast.makeText(context, "Clicked on recipe: $recipeId", Toast.LENGTH_SHORT).show()
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OpenCrumbPreview() {
    OpenCrumbTheme {
        OpenCrumbApp()
    }
}
