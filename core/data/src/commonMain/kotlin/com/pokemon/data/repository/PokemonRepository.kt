package com.pokemon.data.repository

import com.pokemon.common.result.Result
import com.pokemon.model.Pokemon
import com.pokemon.model.SimplePokemon

interface PokemonRepository {
    suspend fun getPokemonList(): Result<List<SimplePokemon>>
    suspend fun getPokemon(name: String): Result<Pokemon>
}