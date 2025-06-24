package feature.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.ui.composable.AdaptiveLayout
import core.ui.composable.AppBarHeight
import core.ui.composable.AppIconButton
import core.ui.composable.AppTopBar
import core.ui.painter.closePainter
import core.ui.painter.starPainter
import core.ui.theme.AppShape

@Composable
fun HomeBarWithShadow(
    searchQuery: String,
    onSearch: (String) -> Unit,
    onGoFavorite: () -> Unit
) {
    Column {
        AdaptiveLayout(
            compactContent = {
                CompactHomeBar(
                    searchQuery = searchQuery,
                    onSearch = onSearch,
                    onGoFavorite = onGoFavorite
                )
            },
            expandedContent = {
                ExpandHomeBar(
                    searchQuery = searchQuery,
                    onSearch = onSearch,
                    onGoFavorite = onGoFavorite
                )
            }
        )
    }
}

@Composable
private fun CompactHomeBar(
    searchQuery: String,
    onSearch: (String) -> Unit,
    onGoFavorite: () -> Unit
) {
    AppTopBar(
        modifier = Modifier.height(AppBarHeight.WideHeight),
        shape = AppShape.BottomOnlyRoundedShape,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
    searchQuery: String,
    onSearch: (String) -> Unit,
    onGoFavorite: () -> Unit
) {
    AppTopBar(Modifier.height(AppBarHeight.BasicHeight)) {
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