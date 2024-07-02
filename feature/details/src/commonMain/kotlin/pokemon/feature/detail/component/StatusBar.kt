package pokemon.feature.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val statColors = listOf(
    Color(0xFF4CAF50),
    Color(0xFFF44336),
    Color(0xFF2196F3),
    Color(0xFFFFC107),
    Color(0xFF9C27B0),
    Color(0xFF00BCD4)
)

@Composable
fun StatusBar(
    modifier: Modifier = Modifier,
    label: String,
    value: Int,
    percentage: Float,
    color: Color
) {
    val outerShape = RoundedCornerShape(5.dp)
    val innerShape =
        RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 5.dp, bottomEnd = 5.dp)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.width(60.dp)
        )
        Spacer(Modifier.width(6.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(shape = outerShape)
                .background(color)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(0.5.dp)
                    .clip(shape = innerShape)
                    .fillMaxHeight()
                    .fillMaxWidth(1f - percentage)
                    .background(Color.White)
            )

            Text(
                text = "$value/100",
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}