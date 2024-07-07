@file:OptIn(ExperimentalFoundationApi::class)

package pokemon.feature.detail.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ui.composable.AppImage
import ui.theme.Gray95

@Composable
fun ImagePager(
    images: List<String>,
    backgroundPainter: Painter?,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(
        pageCount = { images.size }
    )
    Box(
        modifier = modifier
    ) {
        BackgroundContent(
            modifier = Modifier.fillMaxSize(),
            backgroundPainter = backgroundPainter
        )
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
        ) { page ->
            ImageContent(
                imageUrl = images[page],
                modifier = Modifier.fillMaxSize()
            )
        }
        // Indicators
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(times = pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Gray95 else Color.Transparent
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .border(1.dp, Gray95, shape = CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
    }
}

@Composable
private fun ImageContent(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        AppImage(
            imageUrl = imageUrl,
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        )
    }
}

@Composable
private fun BackgroundContent(
    backgroundPainter: Painter?,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        backgroundPainter?.also {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = backgroundPainter,
                contentDescription = "background",
                contentScale = ContentScale.FillBounds
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White.copy(alpha = 0.75f))
        )
    }
}