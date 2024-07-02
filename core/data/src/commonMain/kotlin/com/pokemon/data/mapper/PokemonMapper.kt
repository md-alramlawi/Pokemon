package com.pokemon.data.mapper

import com.pokemon.model.Pokemon
import com.pokemon.network.service.dto.PokemonDto

val PokemonDto.toPokemon: Pokemon
    get() = Pokemon(
        id = this.id.toString(),
        name = this.name,
        imageUrl = this.sprites.other.home.frontDefault,
        types = this.types.map { it.type.name },
        weight = this.weight.div(10.0),
        height = this.height.times(10.0),
        stats = this.stats.map {
            Pokemon.Stat(
                name = it.stat.name.abbreviation,
                baseStat = it.baseStat,
                effort = it.effort,
                percentage = it.baseStat.toFloat().div(100.0f)
            )
        }
    )

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