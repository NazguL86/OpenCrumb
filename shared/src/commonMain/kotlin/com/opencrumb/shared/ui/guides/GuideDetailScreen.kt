package com.opencrumb.shared.ui.guides

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.opencrumb.shared.data.model.Guide
import com.opencrumb.shared.ui.getDrawableResourceByName
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideDetailScreen(
    guide: Guide,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
            }
            Text(
                text = guide.title,
                style = MaterialTheme.typography.headlineMedium
            )
        }
        ParsedMarkdownContent(guide.content)
    }
}

@Composable
fun ParsedMarkdownContent(content: String) {
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
                
                // Display in grid
                val columns = when {
                    images.size == 4 -> 4
                    images.size >= 6 -> 3
                    else -> 2
                }
                val rows = (images.size + columns - 1) / columns
                
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    for (row in 0 until rows) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            for (col in 0 until columns) {
                                val index = row * columns + col
                                if (index < images.size) {
                                    Image(
                                        painter = painterResource(getDrawableResourceByName(images[index])),
                                        contentDescription = null,
                                        modifier = Modifier.weight(1f).aspectRatio(1f),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
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
