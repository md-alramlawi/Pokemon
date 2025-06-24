package core.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.ui.brush.shadowBrush
import core.ui.theme.Gray95

object AppBarHeight {
    val BasicHeight: Dp = 130.dp
    val WideHeight: Dp = 240.dp
}

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    background: Color = Color.Red,
    shape: Shape = RoundedCornerShape(0.dp),
    content: @Composable (RowScope.() -> Unit),
) {
    Column(modifier.fillMaxWidth()) {
        Row(
            Modifier
                .weight(1f)
                .background(Gray95)
                .clip(shape)
                .background(background)
                .windowInsetsPadding(WindowInsets.statusBars)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            content.invoke(this)
        }
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(15.dp)
                .background(shadowBrush()),
        )
    }
}
