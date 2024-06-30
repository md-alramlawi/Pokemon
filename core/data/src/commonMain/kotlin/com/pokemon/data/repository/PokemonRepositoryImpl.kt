package com.pokemon.data.repository

import com.pokemon.common.result.Result
import com.pokemon.common.result.mapSuccess
import com.pokemon.data.mapper.toPokemon
import com.pokemon.model.Pokemon
import com.pokemon.model.SimplePokemon
import com.pokemon.network.datasource.pokemon.PokemonDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PokemonRepositoryImpl(private val dataSource: PokemonDataSource) : PokemonRepository {

    private val pokemonList = mutableListOf<SimplePokemon>()
    override val current = MutableStateFlow("")

    override suspend fun setCurrent(name: String): Result<Unit> {
        return pokemonList.firstOrNull { it.name == name }?.let {
            current.emit(it.name)
            Result.Success(Unit)
        } ?: Result.Error(Exception("Not found"))
    }

    override suspend fun getPokemonList(): Result<List<SimplePokemon>> {
        if (pokemonList.isNotEmpty()) {
            return Result.Success(pokemonList)
        }
        return dataSource.getPokemonListing().mapSuccess { listing ->
            listing.results.map { it.toPokemon }.also {
                pokemonList.clear()
                pokemonList.addAll(it)
            }
        }
    }

    override suspend fun getPokemon(name: String): Result<Pokemon> {
        return dataSource.getPokemon(name).mapSuccess { dto ->
            dto.toPokemon
        }
    }
}