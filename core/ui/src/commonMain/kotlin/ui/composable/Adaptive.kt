package ui.composable

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun AdaptiveLayout(
    compactContent: @Composable () -> Unit,
    expandedContent: @Composable () -> Unit
) {
    BoxWithConstraints {
        if (maxWidth <= 600.dp) {
            compactContent()
        } else {
            expandedContent()
        }
    }
}

@Composable
fun <T> AdaptiveLayout(
    compactContent: T,
    expandedContent: T,
    fraction: Float = 1.0f,
    onSelect: @Composable (T) -> Unit
) {
    BoxWithConstraints {
        if (maxWidth <= 600.dp * fraction) {
            onSelect(compactContent)
        } else {
            onSelect(expandedContent)
        }
    }
}