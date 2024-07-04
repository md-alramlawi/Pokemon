package pokemon.feature.home

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.SimplePokemon
import org.koin.compose.koinInject
import pokemon.feature.home.composable.AppHeaderWithShadow
import pokemon.feature.home.composable.PokemonItem
import ui.composable.AppErrorDialog
import ui.composable.ShimmerEffect
import ui.state.UIEvent
import ui.state.UserAction

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinInject(),
    onClickItem: () -> Unit,
    onGoFavorite: () -> Unit
) {

    val list by viewModel.list.collectAsState()
    val query by viewModel.query.collectAsState()
    val bookmarkIds by viewModel.bookmarkIds.collectAsState()
    val uiEvent by viewModel.uiEvents.collectAsState()

    PokemonListContent(
        isLoading = uiEvent is UIEvent.Loading,
        list = list,
        bookmarkIds = bookmarkIds,
        onClickItem = {
            viewModel.setCurrent(it.id)
            onClickItem()
        },
        onClickSave = { viewModel.bookmark(it.id) },
        onSearch = viewModel::search,
        searchQuery = query,
        onGoFavorite = onGoFavorite
    )

    if (uiEvent is UIEvent.Error) {
        AppErrorDialog((uiEvent as UIEvent.Error).message) { viewModel.onAction(UserAction.Release) }
    }
}


@Composable
private fun PokemonListContent(
    isLoading: Boolean,
    list: List<SimplePokemon>,
    bookmarkIds: List<String>,
    onClickItem: (SimplePokemon) -> Unit,
    onClickSave: (SimplePokemon) -> Unit,
    searchQuery: String,
    onSearch: (String) -> Unit,
    onGoFavorite: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            contentPadding = PaddingValues(
                start = 5.dp,
                end = 5.dp,
                top = 250.dp,
                bottom = 20.dp
            )
        ) {
            if (isLoading) {
                items(12) {
                    ShimmerEffect(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        shape = MaterialTheme.shapes.medium
                    )
                }
                return@LazyVerticalGrid
            }
            items(list, key = { it.name }) { pokemon ->
                PokemonItem(
                    id = pokemon.id,
                    name = pokemon.name,
                    imageUrl = pokemon.url,
                    isFavorite = bookmarkIds.contains(pokemon.id),
                    onClick = { onClickItem(pokemon) },
                    onClickSave = { onClickSave(pokemon) }
                )
            }
        }

        AppHeaderWithShadow(
            modifier = Modifier.height(240.dp),
            searchQuery = searchQuery,
            onSearch = onSearch,
            onGoFavorite = onGoFavorite
        )
    }
}