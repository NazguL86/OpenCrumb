package com.opencrumb.app.ui.recipelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.opencrumb.app.R
import com.opencrumb.app.data.model.Ingredient
import com.opencrumb.app.data.model.Recipe
import com.opencrumb.app.data.model.RecipeCategory

@Composable
fun RecipeListScreen(
    recipes: List<Recipe>,
    categories: List<RecipeCategory>,
    selectedCategory: RecipeCategory,
    onCategorySelected: (RecipeCategory) -> Unit,
    onRecipeClick: (Int) -> Unit,
) {
    Column {
        TabRow(selectedTabIndex = categories.indexOf(selectedCategory)) {
            categories.forEach { category ->
                Tab(
                    selected = category == selectedCategory,
                    onClick = { onCategorySelected(category) },
                    text = { Text(category.displayName) },
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(recipes) { recipe ->
                RecipeListItem(recipe = recipe, onClick = { onRecipeClick(recipe.id) })
            }
        }
    }
}

@Composable
fun RecipeListItem(
    recipe: Recipe,
    onClick: () -> Unit,
) {
    ListItem(
        headlineContent = { Text(recipe.name) },
        supportingContent = {
            Text(
                text = recipe.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        },
        leadingContent = {
            Image(
                painter = painterResource(
                    id = when (recipe.category) {
                        RecipeCategory.PIZZA -> R.drawable.pizza
                        RecipeCategory.FOCACCIA -> R.drawable.focaccia
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        },
        modifier = Modifier.clickable(onClick = onClick),
    )
}

@Preview(showBackground = true)
@Composable
fun RecipeListScreenPreview() {
    val previewRecipes =
        listOf(
            Recipe(
                id = 1,
                name = "Preview Focaccia",
                description = "A delicious preview recipe for a soft and fluffy focaccia that is perfect for sharing as an appetizer or side dish.",
                imageRes = 0,
                ingredients = listOf(Ingredient("Flour", 1.0, "cup")),
                instructions = listOf("Step 1"),
                category = RecipeCategory.FOCACCIA,
            ),
            Recipe(
                id = 2,
                name = "Preview Pizza",
                description = "Another tasty dish, this classic pizza is a family favorite and is incredibly easy to make from scratch.",
                imageRes = 0,
                ingredients = listOf(Ingredient("Sugar", 2.0, "tbsp")),
                instructions = listOf("Do the thing"),
                category = RecipeCategory.PIZZA,
            ),
        )
    RecipeListScreen(
        recipes = previewRecipes.filter { it.category == RecipeCategory.PIZZA },
        categories = listOf(RecipeCategory.FOCACCIA, RecipeCategory.PIZZA),
        selectedCategory = RecipeCategory.PIZZA,
        onCategorySelected = {},
        onRecipeClick = {},
    )
}
