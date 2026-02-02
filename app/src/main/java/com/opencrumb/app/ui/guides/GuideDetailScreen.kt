package com.opencrumb.app.ui.guides

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.opencrumb.app.data.model.Guide

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideDetailScreen(
    guideId: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var guide by remember { mutableStateOf<Guide?>(null) }

    LaunchedEffect(guideId) {
        val json = context.assets.open("guides.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Guide>>() {}.type
        val guides: List<Guide> = Gson().fromJson(json, type)
        guide = guides.find { it.id == guideId }
    }

    guide?.let {
        GuideDetailContent(
            guide = it,
            onBack = onBack,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideDetailContent(
    guide: Guide,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(guide.title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            ParsedMarkdownContent(guide.content)
        }
    }
}

@Composable
fun ParsedMarkdownContent(content: String) {
    val context = LocalContext.current
    val lines = content.split("\n")
    
    var i = 0
    while (i < lines.size) {
        val line = lines[i]
        
        when {
            line.startsWith("![") && line.endsWith("]") -> {
                // Collect consecutive images
                val images = mutableListOf<String>()
                var j = i
                while (j < lines.size && lines[j].startsWith("![") && lines[j].endsWith("]")) {
                    images.add(lines[j].removeSurrounding("![", "]"))
                    j++
                }
                
                // Display images in grid based on count
                val imagesPerRow = when {
                    images.size == 4 -> 4  // 4x1 for exactly 4 images
                    images.size >= 6 -> 3  // 3x3 for 6+ images
                    else -> 2              // 2x2 for other counts
                }
                val rows = images.chunked(imagesPerRow)
                rows.forEach { rowImages ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowImages.forEach { imageName ->
                            val imageResId = remember(imageName) {
                                context.resources.getIdentifier(imageName, "drawable", context.packageName)
                            }
                            if (imageResId != 0) {
                                Image(
                                    painter = painterResource(id = imageResId),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        // Fill remaining space if incomplete row
                        repeat(imagesPerRow - rowImages.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                i = j
                continue
            }
            line.startsWith("**") && line.endsWith("**") -> {
                Text(
                    text = line.removeSurrounding("**"),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
            }
            line.startsWith("- ") -> {
                Text(
                    text = "• ${line.removePrefix("- ")}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                )
            }
            line.matches(Regex("^\\d+\\..*")) -> {
                Text(
                    text = line,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            line.isBlank() -> {
                Spacer(modifier = Modifier.height(8.dp))
            }
            else -> {
                Text(
                    text = line,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
        i++
    }
}

@Preview(showBackground = true)
@Composable
fun GuideDetailScreenPreview() {
    val guide = Guide(
        id = 1,
        title = "Sample Guide",
        description = "This is a sample guide.",
        content = "This is the content of the sample guide. It can be a long text with multiple paragraphs.",
        imageRes = ""
    )
    GuideDetailContent(
        guide = guide,
        onBack = {}
    )
}