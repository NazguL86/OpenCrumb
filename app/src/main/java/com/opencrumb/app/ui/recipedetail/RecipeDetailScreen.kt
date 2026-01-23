package com.opencrumb.app.ui.recipedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Column(modifier = modifier.padding(16.dp)) {
        Image(
            painter =
                painterResource(
                    id =
                        when (recipe.category) {
                            RecipeCategory.PIZZA -> R.drawable.pizza
                            RecipeCategory.FOCACCIA -> R.drawable.focaccia
                        },
                ),
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
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(8.dp))
        IngredientsList(ingredients = recipe.ingredients)
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
fun IngredientsList(
    ingredients: List<Ingredient>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        ingredients.forEach { ingredient ->
            Text(text = "• ${ingredient.amount} ${ingredient.unit} ${ingredient.name}")
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
