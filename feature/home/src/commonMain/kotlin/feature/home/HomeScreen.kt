package feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.ui.composable.AdaptiveLayout
import core.ui.composable.AppBarHeight
import core.ui.composable.ErrorDialog
import core.ui.composable.ShimmerEffect
import core.ui.state.UIState
import core.ui.theme.AppShape
import feature.home.composable.HomeBarWithShadow
import feature.home.composable.PokemonItem
import model.SimplePokemon
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onClickItem: (name: String) -> Unit,
    onGoFavorite: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val data by viewModel.homeData.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchInitialData()
    }

    PokemonListContent(
        isLoading = uiState is UIState.Loading,
        data = data,
        onClickItem = { onClickItem(it.name) },
        onClickSave = viewModel::bookmark,
        onLoadNext = viewModel::loadNextPage,
        onSearch = viewModel::changeSearchQuery,
        onGoFavorite = onGoFavorite,
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
internal fun PokemonListContent(
    isLoading: Boolean,
    data: HomeData,
    onClickItem: (SimplePokemon) -> Unit,
    onClickSave: (SimplePokemon) -> Unit,
    onLoadNext: () -> Unit,
    onSearch: (String) -> Unit,
    onGoFavorite: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter,
    ) {
        AdaptiveLayout(
            compactContent = AppBarHeight.WideHeight,
            expandedContent = AppBarHeight.BasicHeight,
        ) { padding ->
            if (isLoading && data.pokemonList.isEmpty()) {
                LoadingGridContent(padding)
            } else {
                DataGridContent(
                    topPadding = padding,
                    isLoading = isLoading,
                    list = data.pokemonList.search(data.searchQuery),
                    bookmarkIds = data.bookmarkIds,
                    hasNext = data.hasNext == true,
                    onClickItem = onClickItem,
                    onClickSave = onClickSave,
                    onLoadMore = onLoadNext,
                )
            }
        }

        HomeBarWithShadow(
            searchQuery = data.searchQuery,
            onSearch = onSearch,
            onGoFavorite = onGoFavorite,
        )
    }
}

@Composable
private fun LoadingGridContent(topPadding: Dp) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(
            start = 5.dp,
            end = 5.dp,
            top = topPadding,
            bottom = 20.dp,
        ),
    ) {
        items(12) {
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = AppShape.MediumRoundedCornerShape,
            )
        }
    }
}

@Composable
private fun DataGridContent(
    topPadding: Dp,
    isLoading: Boolean,
    list: List<SimplePokemon>,
    bookmarkIds: List<String>,
    hasNext: Boolean,
    onClickItem: (SimplePokemon) -> Unit,
    onClickSave: (SimplePokemon) -> Unit,
    onLoadMore: () -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(
            start = 5.dp,
            end = 5.dp,
            top = topPadding,
            bottom = 20.dp,
        ),
    ) {
        items(list, key = { it.name }) { pokemon ->
            PokemonItem(
                id = pokemon.id,
                name = pokemon.name,
                iconUrl = pokemon.url,
                isBookmarked = bookmarkIds.contains(pokemon.id),
                onClick = { onClickItem(pokemon) },
                onClickSave = { onClickSave(pokemon) },
            )
        }
        item {
            if (!hasNext) return@item

            LaunchedEffect(true) {
                onLoadMore()
            }
            if (isLoading) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }
    }
}
