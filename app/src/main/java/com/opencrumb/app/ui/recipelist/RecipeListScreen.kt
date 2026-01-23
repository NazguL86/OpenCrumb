package com.opencrumb.app.ui.recipelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.opencrumb.app.data.model.Ingredient
import com.opencrumb.app.data.model.Recipe

@Composable
fun RecipeListScreen(recipes: List<Recipe>, onRecipeClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(recipes) { recipe ->
            RecipeListItem(recipe = recipe, onClick = { onRecipeClick(recipe.id) })
        }
    }
}

@Composable
fun RecipeListItem(recipe: Recipe, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(recipe.name) },
        supportingContent = { Text(recipe.description) },
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Preview(showBackground = true)
@Composable
fun RecipeListScreenPreview() {
    val previewRecipes = listOf(
        Recipe(
            id = 1,
            name = "Preview Recipe 1",
            description = "A delicious preview recipe",
            imageRes = 0,
            ingredients = listOf(Ingredient("Flour", 1.0, "cup")),
            instructions = listOf("Step 1")
        ),
        Recipe(
            id = 2,
            name = "Preview Recipe 2",
            description = "Another tasty dish",
            imageRes = 0,
            ingredients = listOf(Ingredient("Sugar", 2.0, "tbsp")),
            instructions = listOf("Do the thing")
        )
    )
    RecipeListScreen(recipes = previewRecipes, onRecipeClick = {})
}
