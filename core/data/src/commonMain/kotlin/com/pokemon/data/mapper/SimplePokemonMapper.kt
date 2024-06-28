package com.pokemon.data.mapper

import com.pokemon.model.SimplePokemon
import com.pokemon.network.service.response.PokemonListing

val PokemonListing.Pokemon.toPokemon: SimplePokemon
    get() = SimplePokemon(
        name = name,
        url = url
    )