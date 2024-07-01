package com.pokemon.data.mapper

import com.pokemon.model.SimplePokemon
import com.pokemon.network.service.response.PokemonListing
import com.pokemon.network.util.ApiConstant

val PokemonListing.Pokemon.toPokemon: SimplePokemon
    get() {
        val id = url.split("/").let { it[it.size - 2] }
        return SimplePokemon(
            id = id,
            name = name,
            url = "${ApiConstant.ICON_BASE_URL}$id.png"
        )
    }