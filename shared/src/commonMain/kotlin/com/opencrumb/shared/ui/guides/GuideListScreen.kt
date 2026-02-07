package com.opencrumb.shared.ui.guides

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import opencrumb.shared.generated.resources.Res
import opencrumb.shared.generated.resources.buy_the_book
import opencrumb.shared.generated.resources.buy_the_book_description
import opencrumb.shared.generated.resources.buy_the_book_url
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

data class GuideItem(
    val title: String,
    val description: String,
    val guideId: Int? = null,
    val externalUrl: String? = null,
    val imageRes: String = "open_crumb_logo",
)

@Composable
fun GuideListScreen(
    uiState: GuideUiState,
    onGuideClick: (Int) -> Unit,
    onExternalUrlClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val guideItemsFromState =
        uiState.guides.map { guide ->
            GuideItem(
                title = guide.title,
                description = guide.description,
                guideId = guide.id,
                imageRes = guide.imageRes,
            )
        }

    val guideItems =
        listOf(
            GuideItem(
                title = stringResource(Res.string.buy_the_book),
                description = stringResource(Res.string.buy_the_book_description),
                externalUrl = stringResource(Res.string.buy_the_book_url),
                imageRes = "open_crumb_book",
            ),
        ) + guideItemsFromState

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text(
                text = "Guides",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }

        items(guideItems) { item ->
            GuideCard(
                guideItem = item,
                onClick = {
                    item.externalUrl?.let { url ->
                        onExternalUrlClick(url)
                    }
                    item.guideId?.let { id ->
                        onGuideClick(id)
                    }
                },
            )
        }
    }
}

@Composable
fun GuideCard(
    guideItem: GuideItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter =
                    painterResource(
                        com.opencrumb.shared.ui
                            .getDrawableResourceByName(guideItem.imageRes),
                    ),
                contentDescription = null,
                modifier =
                    Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = guideItem.title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = guideItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
