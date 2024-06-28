package com.pokemon.network.datasource.pokemon

import com.pokemon.common.result.Result
import com.pokemon.network.service.dto.PokemonDto
import com.pokemon.network.service.response.PokemonListing

interface PokemonDataSource {

    suspend fun getPokemonListing(): Result<PokemonListing>

    suspend fun getPokemon(name: String): Result<PokemonDto>
}