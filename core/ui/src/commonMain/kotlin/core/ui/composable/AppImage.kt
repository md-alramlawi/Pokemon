package core.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun AppImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit,
) {
    KamelImage(
        resource = { asyncPainterResource(data = imageUrl) },
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = Alignment.Center,
        contentScale = contentScale,
        alpha = DefaultAlpha,
        contentAlignment = Alignment.Center,
    )
}
