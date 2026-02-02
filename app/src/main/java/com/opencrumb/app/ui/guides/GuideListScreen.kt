package com.opencrumb.app.ui.guides

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.opencrumb.app.data.model.Guide
import com.opencrumb.app.ui.theme.OpenCrumbTheme

data class GuideItem(
    val title: String,
    val description: String,
    val imageRes: String = "guide",
    val guideId: Int? = null,
    val externalUrl: String? = null
)

@Composable
fun GuideListScreen(
    uiState: GuideUiState,
    onNavigateToGuide: (Int) -> Unit,
    modifier: Modifier = Modifier,
    showBook: Boolean = true
) {
    val context = LocalContext.current

    val guideItemsFromState = uiState.guides.map { guide ->
        GuideItem(
            title = guide.title,
            description = guide.description,
            imageRes = guide.imageRes,
            guideId = guide.id
        )
    }

    val guideItems = if (showBook) {
        listOf(
            GuideItem(
                title = "Buy the Book",
                description = "Purchase 'The Elusive Open Crumb' on Amazon",
                imageRes = "open_crumb_book",
                externalUrl = "https://www.amazon.com/ELUSIVE-OPEN-CRUMB-perfection-Techniques/dp/B08VCJ52G4/"
            )
        ) + guideItemsFromState
    } else {
        guideItemsFromState
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Guides",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(guideItems) { item ->
            GuideCard(
                guideItem = item,
                onClick = {
                    item.externalUrl?.let { url ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                    item.guideId?.let { id ->
                        onNavigateToGuide(id)
                    }
                }
            )
        }
    }
}

@Composable
fun GuideCard(
    guideItem: GuideItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageResId = context.resources.getIdentifier(
        guideItem.imageRes,
        "drawable",
        context.packageName
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = guideItem.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Crop,
                    alignment = BiasAlignment(0f, -0.4f)
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = guideItem.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = guideItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuideListScreenPreview() {
    OpenCrumbTheme {
        GuideListScreen(
            uiState = GuideUiState(
                guides = listOf(
                    Guide(
                        id = 1,
                        title = "Sourdough Basics",
                        description = "A beginner's guide to sourdough.",
                        imageRes = "guide",
                        content = "This is some content for the first guide."
                    ),
                    Guide(
                        id = 2,
                        title = "Advanced Techniques",
                        description = "Mastering the art of the open crumb.",
                        imageRes = "guide",
                        content = "This is some content for the second guide."
                    )
                )
            ),
            onNavigateToGuide = {},
            showBook = false
        )
    }
}
