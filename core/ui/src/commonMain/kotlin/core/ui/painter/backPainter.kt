package core.ui.painter

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.painterResource
import pokemoncm.core.ui.generated.resources.Res
import pokemoncm.core.ui.generated.resources.baseline_arrow_back


@Composable
fun backPainter(): Painter = painterResource(Res.drawable.baseline_arrow_back)