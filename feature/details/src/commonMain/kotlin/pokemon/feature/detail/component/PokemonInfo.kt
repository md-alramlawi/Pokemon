package pokemon.feature.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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

@Composable
fun CompactPokemonInfo(
    modifier: Modifier = Modifier,
    name: String,
    height: Double,
    weight: Double,
    types: List<String>,
    isBookmarked: Boolean,
    onBookmark: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
            BookmarkIconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                isBookmarked = isBookmarked
            ) {
                onBookmark()
            }
        }
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            types.forEach {
                TagItem(it.uppercase())
            }
        }
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            PropertyItem(label = "weight", content = "$weight KG")
            PropertyItem(label = "height", content = "$height CM")
        }
    }
}

@Composable
fun ExpandedPokemonInfo(
    modifier: Modifier = Modifier,
    name: String,
    height: Double,
    weight: Double,
    types: List<String>,
    isBookmarked: Boolean,
    onBookmark: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(end = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            BookmarkIconButton(isBookmarked) { onBookmark() }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            types.forEach { TagItem(it.uppercase()) }
            Spacer(Modifier.width(20.dp))
            PropertyItem(label = "weight", content = "$weight KG")
            PropertyItem(label = "height", content = "$height CM")
        }
    }
}