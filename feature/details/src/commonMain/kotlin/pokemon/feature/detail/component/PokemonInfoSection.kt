@file:OptIn(ExperimentalLayoutApi::class)

package pokemon.feature.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.composable.BookmarkIconButton
import ui.composable.TagItem


@Composable
internal fun CompactPokemonInfo(
    modifier: Modifier = Modifier,
    name: String,
    height: Double,
    weight: Double,
    types: List<String>,
    isBookmarked: Boolean,
    onBookmark: () -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            BookmarkIconButton(isBookmarked = isBookmarked) { onBookmark() }
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            types.forEach {
                TagItem(
                    modifier = Modifier.width(100.dp),
                    tag = it.uppercase()
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            PropertyItem(label = "Weight", content = "$weight KG")
            PropertyItem(label = "Height", content = "$height CM")
        }
    }
}

@Composable
internal fun ExpandedPokemonInfo(
    modifier: Modifier = Modifier,
    name: String,
    height: Double,
    weight: Double,
    types: List<String>,
    isBookmarked: Boolean,
    onBookmark: () -> Unit,
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                BookmarkIconButton(isBookmarked = isBookmarked) { onBookmark() }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                types.forEach {
                    TagItem(
                        modifier = Modifier.width(100.dp),
                        tag = it.uppercase()
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PropertyItem(label = "Weight", content = "$weight KG")
            PropertyItem(label = "Height", content = "$height CM")
        }
    }
}

@Composable
private fun PropertyItem(
    modifier: Modifier = Modifier,
    label: String,
    content: String,
    color: Color = Color.White
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = content,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = color
        )
        Text(
            text = label,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.labelSmall,
            color = color.copy(alpha = 0.5f)
        )
    }
}