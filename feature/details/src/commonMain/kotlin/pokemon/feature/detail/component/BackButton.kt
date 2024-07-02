package pokemon.feature.detail.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pokemon.ui.painter.backPainter

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    IconButton(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 20.dp),
        onClick = onBack
    ) {
        Icon(
            painter = backPainter(),
            contentDescription = null
        )
    }
}
