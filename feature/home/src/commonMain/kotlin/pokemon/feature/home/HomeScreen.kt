package pokemon.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import model.SimplePokemon
import org.koin.compose.koinInject
import pokemon.feature.home.composable.*
import ui.composable.AdaptiveLayout
import ui.composable.ErrorDialog
import ui.composable.ShimmerEffect
import ui.state.UIEvent
import ui.state.UserAction
import ui.theme.AppShape

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinInject(),
    onClickItem: () -> Unit,
    onGoFavorite: () -> Unit
) {
    val list by viewModel.currentList.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
    val bookmarkIds by viewModel.bookmarkIds.collectAsState()
    val uiEvent by viewModel.uiEvents.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()

    PokemonListContent(
        initLoading = uiEvent is UIEvent.Loading,
        isLoadingMore = isLoadingMore,
        list = list,
        bookmarkIds = bookmarkIds,
        onClickItem = {
            viewModel.setCurrentPokemon(it.name)
            onClickItem()
        },
        onClickSave = viewModel::bookmarkPokemon,
        onLoadMore = viewModel::loadMoreItems,
        onSearch = viewModel::searchItems,
        searchQuery = query,
        onGoFavorite = onGoFavorite
    )

    if (uiEvent is UIEvent.Error) {
        ErrorDialog((uiEvent as UIEvent.Error).message) {
            viewModel.onAction(UserAction.Release)
        }
    }
}

@Composable
private fun PokemonListContent(
    initLoading: Boolean,
    isLoadingMore: Boolean,
    list: List<SimplePokemon>,
    bookmarkIds: List<String>,
    onClickItem: (SimplePokemon) -> Unit,
    onClickSave: (SimplePokemon) -> Unit,
    onLoadMore: () -> Unit,
    searchQuery: String,
    onSearch: (String) -> Unit,
    onGoFavorite: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        AdaptiveLayout(
            compactContent = CompactHomeBarHeight,
            expandedContent = ExpandedHomeBarHeight
        ) { topPadding ->
            if (initLoading) {
                LoadingGridContent(topPadding)
            } else {
                DataGridContent(
                    topPadding = topPadding,
                    isLoadingMore = isLoadingMore,
                    list = list,
                    bookmarkIds = bookmarkIds,
                    onClickItem = onClickItem,
                    onClickSave = onClickSave,
                    onLoadMore = onLoadMore
                )
            }
        }

        HomeBarWithShadow(
            searchQuery = searchQuery,
            onSearch = onSearch,
            onGoFavorite = onGoFavorite
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
            bottom = 20.dp
        )
    ) {
        items(12) {
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = AppShape.MediumRoundedCornerShape
            )
        }
    }
}

@Composable
private fun DataGridContent(
    topPadding: Dp,
    isLoadingMore: Boolean,
    list: List<SimplePokemon>,
    bookmarkIds: List<String>,
    onClickItem: (SimplePokemon) -> Unit,
    onClickSave: (SimplePokemon) -> Unit,
    onLoadMore: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(
            start = 5.dp,
            end = 5.dp,
            top = topPadding,
            bottom = 20.dp
        )
    ) {
        items(list, key = { it.name }) { pokemon ->
            PokemonItem(
                id = pokemon.id,
                name = pokemon.name,
                iconUrl = pokemon.url,
                isBookmarked = bookmarkIds.contains(pokemon.id),
                onClick = { onClickItem(pokemon) },
                onClickSave = { onClickSave(pokemon) }
            )
        }
        item {
            LaunchedEffect(true) {
                coroutineScope.launch { onLoadMore() }
            }
            if (isLoadingMore) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }
    }
}