package ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.painter.starOutlinePainter
import ui.painter.starPainter

@Composable
fun BookmarkIconButton(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AppIconButton(
        modifier = modifier,
        painter = if (isFavorite) {
            starPainter()
        } else {
            starOutlinePainter()
        },
        tint = if (isFavorite) {
            Color.Red
        } else Color.Gray,
        size = 32.dp
    ) {
        onClick()
    }
}
