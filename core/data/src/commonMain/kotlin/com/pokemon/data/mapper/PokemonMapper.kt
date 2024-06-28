package com.pokemon.data.mapper

import com.pokemon.model.Pokemon
import com.pokemon.network.service.dto.PokemonDto

val PokemonDto.toPokemon: Pokemon
    get() = Pokemon(
        name = this.name,
        imageUrl = this.sprites.other.home.frontDefault,
        types = this.types.map { it.type.name },
        weight = this.weight,
        height = this.height
    )