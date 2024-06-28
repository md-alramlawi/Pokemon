package com.pokemon.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pokemon.ui.screens.detail.DetailScreen
import com.pokemon.model.SimplePokemon
import com.pokemon.model.UIEvent
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

data object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: HomeScreenModel = getScreenModel()

        val list by screenModel.list.collectAsState()
        val uiEvent by screenModel.uiEvent.collectAsState()

        LaunchedEffect(Unit) {
            screenModel.getList()
        }

        PokemonListContent(
            list = list,
            onClickItem = { navigator.push(DetailScreen(it.name)) }
        )

        when(uiEvent){
            is UIEvent.Loading -> { LoadingOverlay() }
            is UIEvent.Failure -> {
                ErrorDialog((uiEvent as UIEvent.Failure).message) { screenModel.resetUIEvent() }
            }
            else -> {}
        }

    }
}

@Composable
private fun PokemonListContent(
    list: List<SimplePokemon>,
    onClickItem: (SimplePokemon) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(list, key = { it.name }) { item ->
            PokemonItem(pokemon = item, onClick = onClickItem)
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
            KamelImage(
                resource = asyncPainterResource(data = pokemonIcon(pokemon.id)),
                contentDescription = pokemon.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(42.dp)
                    .height(42.dp)
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

