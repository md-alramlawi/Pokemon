package core.ui.brush

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import core.ui.theme.Gray95

internal val ShadowColors =
    listOf(
        Gray95,
        Color.Transparent,
    )

fun shadowBrush(colors: List<Color> = ShadowColors) = Brush.verticalGradient(colors)
