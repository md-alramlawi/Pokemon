package pokemon.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.pokemon.model.SimplePokemon
import com.pokemon.model.UIEvent
import com.pokemon.ui.composable.AppImage
import com.pokemon.ui.composable.ErrorDialog
import com.pokemon.ui.composable.LoadingOverlay
import com.pokemon.ui.theme.PokemonTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import pokemon.feature.home.composable.AppHeaderWithShadow

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
            .background(Color.Black),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                start = 10.dp,
                end = 10.dp,
                top = 210.dp,
                bottom = 20.dp
            )
        ) {
            items(list, key = { it.name }) { item ->
                PokemonItem(pokemon = item, onClick = onClickItem)
            }
        }

        AppHeaderWithShadow(
            modifier = Modifier.height(200.dp),
            searchQuery = searchQuery,
            onSearch = onSearch
        )
    }
}

@Composable
private fun PokemonItem(pokemon: SimplePokemon, onClick: (SimplePokemon) -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.clickable { onClick(pokemon) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.End),
                text = "#${pokemon.id}",
                style = MaterialTheme.typography.labelMedium
            )
            AppImage(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = pokemon.url,
                contentDescription = pokemon.name,
                contentScale = ContentScale.FillWidth
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
            )
        }
    }
}


@Preview
@Composable
private fun PokemonPreview() {
    PokemonTheme {
        PokemonItem(
            pokemon = SimplePokemon("2", "pidgy", ""),
            onClick = {}
        )
    }
}