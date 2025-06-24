package core.ui.composable

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
    onSelect: @Composable (T) -> Unit
) {
    BoxWithConstraints {
        if (maxWidth <= 600.dp) {
            onSelect(compactContent)
        } else {
            onSelect(expandedContent)
        }
    }
}