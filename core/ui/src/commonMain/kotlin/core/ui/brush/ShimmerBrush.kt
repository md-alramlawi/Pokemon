package core.ui.brush

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val shimmerColors =
    listOf(
        Color.LightGray,
        Color.LightGray.copy(alpha = 0.5f),
        Color.LightGray,
    )

@Composable
fun shimmerBrush(colors: List<Color> = shimmerColors): Brush {
    val transition = rememberInfiniteTransition()
    val translateAnim =
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec =
            infiniteRepeatable(
                animation =
                tween(
                    durationMillis = 1000,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
        )
    return Brush.linearGradient(
        colors = colors,
        start = Offset(10f, 10f),
        end = Offset(translateAnim.value, translateAnim.value),
    )
}
