package pokemon.feature.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pokemon.ui.brush.shimmerBrush


@Composable
internal fun PropertyItem(
    modifier: Modifier = Modifier,
    label: String,
    content: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = content,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Text(
            text = label,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
    }
}

@Composable
internal fun PropertyItemWithShimmerEffect() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(Color.White)
            .background(brush = shimmerBrush())
    )
}