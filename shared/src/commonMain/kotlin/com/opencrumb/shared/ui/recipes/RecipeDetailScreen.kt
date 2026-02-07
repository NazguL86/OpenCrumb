package com.opencrumb.shared.ui.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.opencrumb.shared.data.model.Recipe
import opencrumb.shared.generated.resources.Res
import opencrumb.shared.generated.resources.open_crumb_logo
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var portions by remember { mutableIntStateOf(recipe.servings) }
    val multiplier = portions.toDouble() / recipe.servings
    
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                }
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
            item {
                Image(
                    painter = painterResource(com.opencrumb.shared.ui.getDrawableResourceByName(recipe.imageRes)),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            
            item {
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = { if (portions > 1) portions-- }) {
                        Text("−", style = MaterialTheme.typography.headlineLarge)
                    }
                    Text(
                        text = "$portions portion${if (portions != 1) "s" else ""}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    TextButton(onClick = { portions++ }) {
                        Text("+", style = MaterialTheme.typography.headlineLarge)
                    }
                }
            }
            
            item {
                Text(
                    text = "Ingredients",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            
            items(recipe.ingredients) { ingredient ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val adjustedAmount = ingredient.amount * multiplier
                    val formattedAmount = if (ingredient.name.contains("flour", ignoreCase = true) || 
                                             ingredient.name.contains("water", ignoreCase = true)) {
                        adjustedAmount.toInt().toString()
                    } else {
                        val rounded = (adjustedAmount * 10).toInt() / 10.0
                        if (rounded % 1.0 == 0.0) {
                            rounded.toInt().toString()
                        } else {
                            val int = rounded.toInt()
                            val dec = ((rounded - int) * 10).toInt()
                            "$int.$dec"
                        }
                    }
                    Text(
                        text = "• $formattedAmount${ingredient.unit} ${ingredient.name}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            
            items(recipe.instructions.size) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${index + 1}. ${recipe.instructions[index]}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
