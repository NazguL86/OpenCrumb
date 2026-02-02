package com.opencrumb.app.ui.recipes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.opencrumb.app.data.model.Ingredient
import com.opencrumb.app.data.model.Recipe
import com.opencrumb.app.data.model.RecipeCategory
import com.opencrumb.app.data.model.RecipeType
import com.opencrumb.app.ui.theme.OpenCrumbTheme
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeListScreen(
    uiState: RecipesUiState,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val categories = uiState.recipesByCategory.keys.sortedBy { it.ordinal }
    val pagerState = rememberPagerState(pageCount = { categories.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier.fillMaxSize()) {
        if (categories.isNotEmpty()) {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                categories.forEachIndexed { index, category ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(category.name.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) }) }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) { page ->
                val category = categories[page]
                val recipesByType = uiState.recipesByCategory[category] ?: emptyMap()
                RecipeCategoryPage(recipesByType = recipesByType, onRecipeClick = onRecipeClick)
            }
        }
    }
}

@Composable
fun RecipeCategoryPage(
    recipesByType: Map<RecipeType, List<Recipe>>,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val doughRecipes = recipesByType[RecipeType.DOUGH] ?: emptyList()
    val toppingRecipes = recipesByType[RecipeType.TOPPING] ?: emptyList()
    
    val recipeTypes = buildList {
        if (doughRecipes.isNotEmpty()) add(RecipeType.DOUGH)
        if (toppingRecipes.isNotEmpty()) add(RecipeType.TOPPING)
    }
    
    var selectedType by remember { mutableStateOf(recipeTypes.firstOrNull() ?: RecipeType.DOUGH) }
    val recipes = if (selectedType == RecipeType.DOUGH) doughRecipes else toppingRecipes

    Column(modifier = modifier.fillMaxSize()) {
        if (recipeTypes.size > 1) {
            SegmentedButton(
                options = recipeTypes,
                selectedOption = selectedType,
                onOptionSelected = { selectedType = it },
                modifier = Modifier.padding(16.dp)
            )
        }
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(recipes) { recipe ->
                if (selectedType == RecipeType.DOUGH) {
                    RecipeListItem(
                        recipe = recipe,
                        onClick = { onRecipeClick(recipe.id) },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                } else {
                    ToppingCard(
                        recipe = recipe,
                        onClick = { onRecipeClick(recipe.id) },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButton(
    options: List<RecipeType>,
    selectedOption: RecipeType,
    onOptionSelected: (RecipeType) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier.fillMaxWidth()) {
        options.forEachIndexed { index, option ->
            SegmentedButton(
                selected = selectedOption == option,
                onClick = { onOptionSelected(option) },
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size)
            ) {
                Text(if (option == RecipeType.DOUGH) "Doughs" else "Toppings")
            }
        }
    }
}


@Composable
fun RecipeListItem(
    recipe: Recipe,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            val context = LocalContext.current
            val imageResId = context.resources.getIdentifier(recipe.imageRes, "drawable", context.packageName)
            val fallbackId = context.resources.getIdentifier("open_crumb_logo", "drawable", context.packageName)

            Image(
                painter = painterResource(id = if (imageResId != 0) imageResId else fallbackId),
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ToppingCard(
    recipe: Recipe,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            val context = LocalContext.current
            val imageResId = context.resources.getIdentifier(recipe.imageRes, "drawable", context.packageName)
            val fallbackId = context.resources.getIdentifier("open_crumb_logo", "drawable", context.packageName)

            Image(
                painter = painterResource(id = if (imageResId != 0) imageResId else fallbackId),
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListScreenPreview() {
    OpenCrumbTheme {
        val sampleRecipes =
            listOf(
                Recipe(
                    id = 1,
                    name = "Focaccia Genovese",
                    description = "A delicious and easy-to-make focaccia, perfect for beginners.",
                    imageRes = "focaccia_genovese",
                    servings = 2,
                    ingredients = listOf(Ingredient("Flour", 550.0, "g")),
                    instructions = listOf("Mix all ingredients in a bowl."),
                    category = RecipeCategory.FOCACCIA,
                    type = RecipeType.DOUGH,
                ),
                Recipe(
                    id = 2,
                    name = "24h Fermentation Pizza",
                    description = "The traditional Italian pizza with tomato, mozzarella, and basil.",
                    imageRes = "pizza",
                    servings = 3,
                    ingredients = listOf(Ingredient("Flour", 424.0, "g")),
                    instructions = listOf("24h fermentation"),
                    category = RecipeCategory.PIZZA,
                    type = RecipeType.DOUGH,
                ),
                Recipe(
                    id = 8,
                    name = "Margherita Topping",
                    description = "The classic Neapolitan topping: simple, fresh, and delicious.",
                    imageRes = "pizza",
                    servings = 1,
                    ingredients = listOf(),
                    instructions = listOf(),
                    category = RecipeCategory.PIZZA,
                    type = RecipeType.TOPPING
                )
            )
        val uiState = RecipesUiState(
            recipesByCategory = sampleRecipes
                .groupBy { it.category }
                .mapValues { it.value.groupBy { it.type } }
        )
        RecipeListScreen(
            uiState = uiState,
            onRecipeClick = {}
        )
    }
}
