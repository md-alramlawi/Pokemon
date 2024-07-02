package pokemon.feature.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pokemon.ui.painter.closePainter
import com.pokemon.ui.theme.roundedBottomShape

@Composable
fun AppHeaderWithShadow(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearch: (String) -> Unit
) {
    Column {
        AppHeader(modifier, searchQuery, onSearch)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            Color.Transparent
                        )
                    )
                )
        )
    }
}

@Composable
fun AppHeader(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearch: (String) -> Unit
) {
    Surface(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        shape = roundedBottomShape,
        color = Color.Red
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 25.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pokedex",
                style = MaterialTheme.typography.displayMedium,
                color = Color.Yellow,
                fontWeight = FontWeight.Bold
            )
            AppSearchBar(
                onSearch = onSearch,
                searchQuery = searchQuery
            )
        }
    }
}


@Composable
private fun AppSearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    hint: String = "Search",
    onSearch: (String) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp)),
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
                onValueChange = { onSearch(it) },
                placeholder = { Text(hint) },
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = { onSearch("") }
                ) {
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