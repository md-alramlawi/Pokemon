package feature.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.ui.composable.AppBarHeight
import core.ui.composable.AppIconButton
import core.ui.composable.AppTopBar
import core.ui.composable.ErrorDialog
import core.ui.painter.backPainter
import core.ui.state.UIState
import feature.home.composable.PokemonItem
import model.SimplePokemon
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = koinViewModel(),
    onClickItem: (name: String) -> Unit,
    onBack: () -> Unit,
) {
    val data by viewModel.bookmarksData.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchInitialData()
    }

    FavoriteContent(
        data = data,
        onClickItem = { onClickItem(it.name) },
        onBack = onBack,
        onClickSave = viewModel::bookmark,
    )

    when (val state = uiState) {
        is UIState.Failure -> {
            ErrorDialog(
                throwable = state.throwable,
                onDismiss = viewModel::releaseState,
            )
        }

        else -> {}
    }
}

@Composable
private fun FavoriteContent(
    data: BookmarkData,
    onClickItem: (SimplePokemon) -> Unit,
    onClickSave: (SimplePokemon) -> Unit,
    onBack: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                contentPadding = PaddingValues(
                    start = 5.dp,
                    end = 5.dp,
                    top = AppBarHeight.BasicHeight,
                    bottom = 20.dp,
                ),
            ) {
                items(data.list, key = { it.name }) { pokemon ->
                    PokemonItem(
                        id = pokemon.id,
                        name = pokemon.name,
                        iconUrl = pokemon.url,
                        isBookmarked = true,
                        onClick = { onClickItem(pokemon) },
                        onClickSave = { onClickSave(pokemon) },
                    )
                }
            }

            AppTopBar(Modifier.height(AppBarHeight.BasicHeight)) {
                BackButton { onBack() }
                Text(
                    text = "Favorite",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun BackButton(
    tint: Color = Color.White,
    backgroundColor: Color = Color.Black.copy(alpha = 0.1f),
    onClick: () -> Unit,
) {
    AppIconButton(
        modifier = Modifier.clip(CircleShape).background(backgroundColor),
        painter = backPainter(),
        tint = tint,
        onClick = onClick,
    )
}
