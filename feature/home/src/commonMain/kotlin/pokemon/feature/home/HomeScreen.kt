package pokemon.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinInject(),
    onClickItem: () -> Unit
) {

    val list by viewModel.list.collectAsState()
    val uiEvent by viewModel.uiEvent.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getList()
    }

    PokemonListContent(
        list = list,
        onClickItem = {
            viewModel.setCurrent(it.name)
            onClickItem()
        }
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
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(list, key = { it.name }) { item ->
                PokemonItem(pokemon = item, onClick = onClickItem)
            }
        }
    }
}

@Composable
private fun PokemonItem(pokemon: SimplePokemon, onClick: (SimplePokemon) -> Unit) {
    fun pokemonIcon(id: String): String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vii/icons/$id.png"

    Surface(
        shadowElevation = 2.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick(pokemon) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AppImage(
                modifier = Modifier.size(42.dp),
                imageUrl = pokemonIcon(pokemon.id),
                contentDescription = pokemon.name,
                contentScale = ContentScale.Crop
            )

            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}


@Preview
@Composable
private fun PokemonPreview(){
    PokemonTheme {
        PokemonItem(
            pokemon = SimplePokemon("pidgy", ""),
            onClick = {}
        )
    }
}