package detail.component

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import model.Pokemon

@Composable
fun BaseStatuses(
    modifier: Modifier = Modifier,
    stats: List<Pokemon.Stat>
) {
    Column(modifier.padding(horizontal = 20.dp)) {
        Text(
            text = "Base Stats",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.surface,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(10.dp))

        stats.forEachIndexed { index, stat ->
            StatusBar(
                modifier = Modifier
                    .fillMaxWidth(),
                label = stat.name.replaceFirstChar { it.uppercase() },
                value = stat.baseStat,
                color = statColors[index]
            )
        }
    }
}

@Composable
private fun StatusBar(
    modifier: Modifier = Modifier,
    label: String,
    value: Int,
    color: Color
) {

    var startAnimation by remember { mutableStateOf(false) }

    val animatedValue by animateIntAsState(
        targetValue = if (startAnimation) value else 0,
        animationSpec = tween(durationMillis = 2_000)
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Row(
        modifier = modifier.padding(vertical = 4.dp),
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
            modifier = Modifier.weight(1f)
        ) {
            LinearProgressIndicator(
                progress = { (animatedValue.toFloat().div(100)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .border(width = 1.dp, color = color),
                color = color,
                trackColor = Color.White,
            )

            Text(
                text = "$animatedValue/100",
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}


private val statColors = listOf(
    Color(0xFF4CAF50),
    Color(0xFFF44336),
    Color(0xFF2196F3),
    Color(0xFFFFC107),
    Color(0xFF9C27B0),
    Color(0xFF00BCD4)
)