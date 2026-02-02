package com.opencrumb.shared.ui.recipes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.opencrumb.shared.data.model.RecipeCategory
import com.opencrumb.shared.data.model.RecipeType
import opencrumb.shared.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    uiState: RecipesUiState,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val categories = uiState.recipesByCategory.keys.sortedBy { it.ordinal }
    var selectedCategory by remember(categories) { 
        mutableStateOf(categories.firstOrNull()) 
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            categories.forEach { category ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { selectedCategory = category }
                        .padding(horizontal = 8.dp)
                ) {
                    Card(
                        modifier = Modifier.size(64.dp),
                        shape = RoundedCornerShape(32.dp),
                        border = if (selectedCategory == category) {
                            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                        } else null
                    ) {
                        Image(
                            painter = painterResource(when (category) {
                                RecipeCategory.PIZZA -> Res.drawable.pizza_icon_top_bar
                                RecipeCategory.FOCACCIA -> Res.drawable.focaccia_icon_top_bar
                                RecipeCategory.BREAD -> Res.drawable.bread_icon_top_bar
                            }),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when (category) {
                            RecipeCategory.PIZZA -> "Pizza"
                            RecipeCategory.FOCACCIA -> "Focaccia"
                            RecipeCategory.BREAD -> "Bread"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = if (selectedCategory == category) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
            }
        }

        selectedCategory?.let { category ->
            val recipesForCategory = uiState.recipesByCategory[category] ?: emptyMap()
            
            if (category == RecipeCategory.PIZZA || category == RecipeCategory.FOCACCIA) {
                var selectedType by remember { mutableStateOf(RecipeType.DOUGH) }
                
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    SegmentedButton(
                        selected = selectedType == RecipeType.DOUGH,
                        onClick = { selectedType = RecipeType.DOUGH },
                        shape = SegmentedButtonDefaults.itemShape(0, 2)
                    ) {
                        Text("Doughs")
                    }
                    SegmentedButton(
                        selected = selectedType == RecipeType.TOPPING,
                        onClick = { selectedType = RecipeType.TOPPING },
                        shape = SegmentedButtonDefaults.itemShape(1, 2)
                    ) {
                        Text("Toppings")
                    }
                }
                
                val recipes = recipesForCategory[selectedType] ?: emptyList()
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(recipes) { recipe ->
                        RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe.id) })
                    }
                }
            } else {
                val allRecipes = recipesForCategory.values.flatten()
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(allRecipes) { recipe ->
                        RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeCard(
    recipe: com.opencrumb.shared.data.model.Recipe,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Image(
                painter = painterResource(com.opencrumb.shared.ui.getDrawableResourceByName(recipe.imageRes)),
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                contentScale = ContentScale.Crop
            )
            
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
