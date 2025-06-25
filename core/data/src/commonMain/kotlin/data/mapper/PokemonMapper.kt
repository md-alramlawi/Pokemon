package data.mapper

import core.network.PokemonDto
import core.network.util.ApiConstant
import model.Pokemon
import model.SimplePokemon

val PokemonDto.toModel: Pokemon
    get() =
        Pokemon(
            id = this.id.toString(),
            name = this.name,
            images = this.images(),
            iconUrl = "${ApiConstant.ICON_BASE_URL}$id.png",
            types = this.types.map { it.type.name },
            weight = this.weight.div(10.0),
            height = this.height.times(10.0),
            stats =
            this.stats.map {
                Pokemon.Stat(
                    name = it.stat.name.abbreviation,
                    baseStat = it.baseStat,
                    effort = it.effort,
                    percentage = it.baseStat.toFloat().div(100.0f),
                )
            },
        )

private fun PokemonDto.images(): List<String> {
    val images = mutableListOf<String>()
    this.sprites.other?.home?.also { home ->
        home.frontDefault?.let { images.add(it) }
        home.frontShiny?.let { images.add(it) }
        home.frontFemale?.let { images.add(it) }
        home.frontShinyFemale?.let { images.add(it) }
    }
    return images
}

private val String.abbreviation: String
    get() {
        return when (this) {
            "hp" -> "HP"
            "attack" -> "ATK"
            "defense" -> "DEF"
            "special-attack" -> "SP-ATK"
            "special-defense" -> "SP-DEF"
            "speed" -> "SPD"
            else -> this.uppercase().take(3)
        }
    }

val Pokemon.toSimple: SimplePokemon
    get() =
        SimplePokemon(
            id = this.id,
            name = this.name,
            url = this.iconUrl,
        )
