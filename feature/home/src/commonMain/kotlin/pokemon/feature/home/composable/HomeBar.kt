package pokemon.feature.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.brush.shadowBrush
import ui.composable.AdaptiveLayout
import ui.composable.AppIconButton
import ui.painter.closePainter
import ui.painter.starPainter
import ui.theme.AppShape

internal val CompactHomeBarHeight: Dp = 240.dp
internal val ExpandedHomeBarHeight: Dp = 120.dp

@Composable
fun HomeBarWithShadow(
    searchQuery: String,
    onSearch: (String) -> Unit,
    onGoFavorite: () -> Unit
) {
    val shadowHeight = 15.dp
    Column {
        AdaptiveLayout(
            compactContent = {
                CompactHomeBar(
                    modifier = Modifier.height(CompactHomeBarHeight - shadowHeight),
                    searchQuery = searchQuery,
                    onSearch = onSearch,
                    onGoFavorite = onGoFavorite
                )
            },
            expandedContent = {
                ExpandHomeBar(
                    modifier = Modifier.height(ExpandedHomeBarHeight - shadowHeight),
                    searchQuery = searchQuery,
                    onSearch = onSearch,
                    onGoFavorite = onGoFavorite
                )
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(shadowHeight)
                .background(shadowBrush())
        )
    }
}

@Composable
private fun CompactHomeBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearch: (String) -> Unit,
    onGoFavorite: () -> Unit
) {
    Surface(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        shape = AppShape.BottomOnlyRoundedShape,
        color = Color.Red
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 25.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeBarHeader(onGoFavorite = onGoFavorite)
            AppSearchBar(
                modifier = Modifier.fillMaxWidth(),
                searchQuery = searchQuery,
                onSearch = onSearch
            )
        }
    }
}

@Composable
private fun ExpandHomeBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearch: (String) -> Unit,
    onGoFavorite: () -> Unit
) {
    Surface(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        color = Color.Red
    ) {
        Box(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pokedex",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Yellow,
                    fontWeight = FontWeight.Bold
                )
                AppSearchBar(
                    modifier = Modifier.weight(1f),
                    searchQuery = searchQuery,
                    onSearch = onSearch
                )
                AppIconButton(
                    painter = starPainter(),
                    tint = Color.White,
                    size = 32.dp
                ) {
                    onGoFavorite()
                }
            }
        }
    }
}

@Composable
private fun HomeBarHeader(onGoFavorite: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.size(24.dp))
        Text(
            text = "Pokedex",
            style = MaterialTheme.typography.displayMedium,
            color = Color.Yellow,
            fontWeight = FontWeight.Bold
        )
        AppIconButton(
            painter = starPainter(),
            tint = Color.White,
            size = 32.dp
        ) {
            onGoFavorite()
        }
    }
}

@Composable
private fun AppSearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearch: (String) -> Unit,
    hint: String = "Search"
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(24.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchQuery,
                onValueChange = onSearch,
                placeholder = { Text(hint) },
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.weight(1f)
            )
            if (searchQuery.isNotEmpty()) {
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { onSearch("") }) {
                    Icon(
                        painter = closePainter(),
                        contentDescription = "Clear Search",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}