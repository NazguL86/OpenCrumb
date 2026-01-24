package com.opencrumb.app.ui.recipedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.opencrumb.app.R
import com.opencrumb.app.data.SampleData
import com.opencrumb.app.data.model.Ingredient
import com.opencrumb.app.data.model.Recipe
import com.opencrumb.app.data.model.RecipeCategory
import com.opencrumb.app.ui.theme.OpenCrumbTheme

@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
    var portions by remember { mutableStateOf(recipe.servings) }

    Column(modifier = modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = recipe.imageRes),
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

        PortionStepper(
            portions = portions,
            onPortionsChange = { newPortions ->
                if (newPortions > 0) {
                    portions = newPortions
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

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
        Text(
            text = "Instructions",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(8.dp))
        InstructionsList(instructions = recipe.instructions)
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
                String.format("%.1f", amount)
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
    OpenCrumbTheme {
        RecipeDetailScreen(recipe = SampleData.recipes.first())
    }
}
