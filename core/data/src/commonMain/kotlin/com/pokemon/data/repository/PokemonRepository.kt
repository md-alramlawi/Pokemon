package com.pokemon.data.repository

import com.pokemon.common.result.Result
import com.pokemon.model.Pokemon
import com.pokemon.model.SimplePokemon
import kotlinx.coroutines.flow.StateFlow

interface PokemonRepository {
    val current: StateFlow<String>
    suspend fun setCurrent(name: String): Result<Unit>
    suspend fun getPokemonList(): Result<List<SimplePokemon>>
    suspend fun getPokemon(name: String): Result<Pokemon>
}