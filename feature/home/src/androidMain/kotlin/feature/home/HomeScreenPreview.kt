package feature.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import core.ui.theme.PokemonTheme
import model.SimplePokemon

@Preview
@Composable
private fun PreviewScreenContent() {
    PokemonTheme {
        PokemonListContent(
            data = HomeData(
                pokemonList = samplePokemons,
                searchQuery = "",
                bookmarkIds = emptyList(),
                hasNext = true,
            ),
            onClickItem = {},
            onClickSave = {},
            onLoadNext = {},
            onSearch = {},
            onGoFavorite = {},
            isLoading = true,
        )
    }
}

val samplePokemons = listOf(
    SimplePokemon(
        id = "1",
        name = "bulbasaur",
        url = "https://pokeapi.co/api/v2/pokemon/1/"
    ),
    SimplePokemon(
        id = "4",
        name = "charmander",
        url = "https://pokeapi.co/api/v2/pokemon/4/"
    ),
    SimplePokemon(
        id = "7",
        name = "squirtle",
        url = "https://pokeapi.co/api/v2/pokemon/7/"
    ),
    SimplePokemon(
        id = "25",
        name = "pikachu",
        url = "https://pokeapi.co/api/v2/pokemon/25/"
    ),
    SimplePokemon(
        id = "39",
        name = "jigglypuff",
        url = "https://pokeapi.co/api/v2/pokemon/39/"
    ),
    SimplePokemon(
        id = "52",
        name = "meowth",
        url = "https://pokeapi.co/api/v2/pokemon/52/"
    ),
    SimplePokemon(
        id = "133",
        name = "eevee",
        url = "https://pokeapi.co/api/v2/pokemon/133/"
    ),
    SimplePokemon(
        id = "150",
        name = "mewtwo",
        url = "https://pokeapi.co/api/v2/pokemon/150/"
    )
)
