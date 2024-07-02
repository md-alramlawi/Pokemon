package pokemon.feature.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
internal fun PropertyItem(
    modifier: Modifier = Modifier,
    title: String,
    content: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )

        Text(
            text = content,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
    }
}