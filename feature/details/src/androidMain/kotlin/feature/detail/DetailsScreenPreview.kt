package feature.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import core.ui.theme.PokemonTheme
import model.Pokemon

@Preview
@Composable
private fun PreviewScreenContent() {
    PokemonTheme {
        PokemonDetailsContent(
            data = DetailsData(
                pokemon = bulbasaur,
            ),
            onClickSave = {},
        )
    }
}

private val bulbasaur = Pokemon(
    id = "001",
    name = "Bulbasaur",
    images = listOf(
        // official artwork and back sprite
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png",
    ),
    iconUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
    types = listOf("Grass", "Poison"),
    stats = listOf(
        Pokemon.Stat(name = "HP", baseStat = 45, effort = 0, percentage = 45 / 255f),
        Pokemon.Stat(name = "Attack", baseStat = 49, effort = 0, percentage = 49 / 255f),
        Pokemon.Stat(name = "Defense", baseStat = 49, effort = 0, percentage = 49 / 255f),
        Pokemon.Stat(name = "Sp. Atk", baseStat = 65, effort = 1, percentage = 65 / 255f),
        Pokemon.Stat(name = "Sp. Def", baseStat = 65, effort = 0, percentage = 65 / 255f),
        Pokemon.Stat(name = "Speed", baseStat = 45, effort = 0, percentage = 45 / 255f),
    ),
    weight = 6.9,
    height = 0.7,
    isBookmarked = false,
)
