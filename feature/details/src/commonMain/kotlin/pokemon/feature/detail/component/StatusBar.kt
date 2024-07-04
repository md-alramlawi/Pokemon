package pokemon.feature.detail.component


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatusBar(
    modifier: Modifier = Modifier,
    label: String,
    value: Int,
    percentage: Float,
    color: Color
) {

    var startAnimation by remember { mutableStateOf(false) }

    val animatedValue by animateIntAsState(
        targetValue = if (startAnimation) value else 0,
        animationSpec = tween(durationMillis = 2_000)
    )
    val animatedPercentage by animateFloatAsState(
        targetValue = if (startAnimation) percentage else 0f,
        animationSpec = tween(durationMillis = 2_000)
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }


    val outerShape = RoundedCornerShape(5.dp)
    val innerShape = RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 5.dp, bottomEnd = 5.dp)

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
                    .fillMaxWidth(1f - animatedPercentage)
                    .background(Color.White)
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



val statColors = listOf(
    Color(0xFF4CAF50),
    Color(0xFFF44336),
    Color(0xFF2196F3),
    Color(0xFFFFC107),
    Color(0xFF9C27B0),
    Color(0xFF00BCD4)
)