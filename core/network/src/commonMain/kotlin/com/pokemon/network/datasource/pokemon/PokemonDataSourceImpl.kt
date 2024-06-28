package com.pokemon.network.datasource.pokemon

import com.pokemon.common.result.Result
import com.pokemon.network.service.api.PokemonApi
import com.pokemon.network.service.dto.PokemonDto
import com.pokemon.network.service.response.PokemonListing
import io.ktor.client.call.body

class PokemonDataSourceImpl(val api: PokemonApi) : PokemonDataSource {
    override suspend fun getPokemonListing(): Result<PokemonListing> {
        return try {
            val result = api.getPokemonListing().body<PokemonListing>()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getPokemon(name: String): Result<PokemonDto> {
        return try {
            val result = api.getPokemon(name).body<PokemonDto>()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}