package pokemon.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pokemon.model.SimplePokemon
import com.pokemon.model.UIEvent
import com.pokemon.ui.composable.ErrorDialog
import com.pokemon.ui.composable.LoadingOverlay
import org.koin.compose.koinInject
import pokemon.feature.home.composable.AppHeaderWithShadow
import pokemon.feature.home.composable.PokemonItem

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinInject(),
    onClickItem: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    val uiEvent by viewModel.uiEvent.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getList()
    }

    PokemonListContent(
        list = state.list,
        onClickItem = {
            viewModel.setCurrent(it.name)
            onClickItem()
        },
        onSearch = viewModel::search,
        searchQuery = state.query
    )

    when (uiEvent) {
        is UIEvent.Loading -> {
            LoadingOverlay()
        }

        is UIEvent.Failure -> {
            ErrorDialog((uiEvent as UIEvent.Failure).message) { viewModel.resetUIEvent() }
        }

        else -> {}
    }
}


@Composable
private fun PokemonListContent(
    list: List<SimplePokemon>,
    onClickItem: (SimplePokemon) -> Unit,
    searchQuery: String,
    onSearch: (String) -> Unit
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
            items(list, key = { it.name }) { item ->
                PokemonItem(pokemon = item, onClick = onClickItem)
            }
        }

        AppHeaderWithShadow(
            modifier = Modifier.height(240.dp),
            searchQuery = searchQuery,
            onSearch = onSearch
        )
    }
}