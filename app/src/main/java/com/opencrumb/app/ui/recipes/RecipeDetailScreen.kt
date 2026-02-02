package com.opencrumb.app.ui.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.opencrumb.app.R
import com.opencrumb.app.data.model.Ingredient
import com.opencrumb.app.data.model.Recipe
import com.opencrumb.app.data.model.RecipeCategory
import com.opencrumb.app.data.model.RecipeType
import com.opencrumb.app.ui.theme.OpenCrumbTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    toppingSuggestions: List<Recipe> = emptyList(),
    bottomContentPadding: Dp = 0.dp,
) {
    var portions by remember { mutableIntStateOf(recipe.servings) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            val context = LocalContext.current
            val imageResId = remember(recipe.imageRes) {
                context.resources.getIdentifier(recipe.imageRes, "drawable", context.packageName)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = if (imageResId != 0) imageResId else R.drawable.open_crumb_logo),
                contentDescription = recipe.name,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (recipe.type == RecipeType.DOUGH && toppingSuggestions.isNotEmpty()) {
                CollapsibleSection(title = "Topping Suggestions") {
                    ToppingSuggestionsList(toppings = toppingSuggestions)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (recipe.type == RecipeType.DOUGH) {
                PortionStepper(
                    portions = portions,
                    onPortionsChange = { newPortions ->
                        if (newPortions > 0) {
                            portions = newPortions
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            val scaleFactor = portions.toDouble() / recipe.servings
            val scaledIngredients = recipe.ingredients.map {
                it.copy(amount = it.amount * scaleFactor)
            }

            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(8.dp))
            IngredientsList(ingredients = scaledIngredients)
            Spacer(modifier = Modifier.height(16.dp))

            if (recipe.instructions.isNotEmpty()) {
                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(modifier = Modifier.height(8.dp))
                InstructionsList(instructions = recipe.instructions)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(bottomContentPadding))
        }
    }
}

@Composable
fun CollapsibleSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (isExpanded) "Collapse" else "Expand"
            )
        }
        if (isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun ToppingSuggestionsList(
    toppings: List<Recipe>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        toppings.forEach { topping ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(topping.name, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    IngredientsList(ingredients = topping.ingredients)
                }
            }
        }
    }
}

@Composable
fun PortionStepper(
    portions: Int,
    onPortionsChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onPortionsChange(portions - 1) }) {
            Icon(Icons.Default.Remove, contentDescription = "Decrease portions")
        }
        Text(
            text = "$portions portion(s)",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        IconButton(onClick = { onPortionsChange(portions + 1) }) {
            Icon(Icons.Default.Add, contentDescription = "Increase portions")
        }
    }
}

@Composable
fun IngredientsList(
    ingredients: List<Ingredient>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        ingredients.forEach { ingredient ->
            val amount = ingredient.amount
            val amountText = if (amount == amount.toInt().toDouble()) {
                amount.toInt().toString()
            } else {
                String.format(Locale.getDefault(), "%.1f", amount)
            }
            Text(text = "• ${ingredient.name} ${amountText}${ingredient.unit}")
        }
    }
}

@Composable
fun InstructionsList(
    instructions: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        instructions.forEachIndexed { index, instruction ->
            Text(text = "${index + 1}. $instruction")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailScreenPreview() {
    val previewTopping = Recipe(
        id = 9,
        name = "Focaccia Genovese Topping",
        description = "A classic Ligurian topping with sea salt, rosemary, and a generous amount of olive oil.",
        imageRes = "focaccia_genovese",
        servings = 1,
        ingredients = listOf(
            Ingredient("Extra Virgin Olive Oil", 2.0, "tbsp"),
            Ingredient("Coarse Sea Salt", 1.0, "tsp"),
            Ingredient("Fresh Rosemary", 2.0, "sprigs")
        ),
        instructions = emptyList(),
        category = RecipeCategory.FOCACCIA,
        type = RecipeType.TOPPING
    )
    val previewRecipe = Recipe(
        id = 1,
        name = "Focaccia Genovese",
        description = "A classic Italian flatbread from Genoa, topped with olive oil and salt.",
        imageRes = "focaccia_genovese",
        servings = 4,
        ingredients = listOf(
            Ingredient("Flour", 500.0, "g"),
            Ingredient("Water", 300.0, "ml"),
            Ingredient("Yeast", 7.0, "g"),
            Ingredient("Salt", 10.0, "g"),
            Ingredient("Olive Oil", 50.0, "ml")
        ),
        instructions = listOf(
            "Mix flour and yeast.",
            "Add water and knead for 10 minutes.",
            "Add salt and olive oil and knead for another 5 minutes.",
            "Let it rise for 2 hours.",
            "Shape it on a baking tray, poke holes, and drizzle with more olive oil and salt.",
            "Bake at 220°C for 20 minutes."
        ),
        category = RecipeCategory.FOCACCIA,
        type = RecipeType.DOUGH
    )
    OpenCrumbTheme {
        RecipeDetailScreen(recipe = previewRecipe, onBack = {}, toppingSuggestions = listOf(previewTopping))
    }
}
