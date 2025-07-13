package core.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.painterResource
import pokemon.core.ui.generated.resources.Res
import pokemon.core.ui.generated.resources.pokeball

@Composable
fun AppImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit,
) {
    if (LocalInspectionMode.current) {
        // Preview stub: use a drawable from resources
        Image(
            modifier = modifier,
            painter = painterResource(Res.drawable.pokeball),
            contentDescription = contentDescription,
            contentScale = contentScale,
        )
    } else {
        // Real runtime loader
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
}
