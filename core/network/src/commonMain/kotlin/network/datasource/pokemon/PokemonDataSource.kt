package network.datasource.pokemon

import common.result.Result
import common.result.mapSuccess
import io.ktor.client.call.body
import network.service.api.PokemonApi
import network.service.dto.PokemonDto
import network.service.response.PokemonListing

interface PokemonDataSource {

    suspend fun getPokemonListing(): Result<PokemonListing>
    suspend fun getNextList(): Result<PokemonListing>
    suspend fun getPokemon(name: String): Result<PokemonDto>
}

class PokemonDataSourceImpl(private val api: PokemonApi) : PokemonDataSource {
    private var nextUrl: String? = null
    override suspend fun getPokemonListing(): Result<PokemonListing> {
        return try {
            val result = api.getInitialList().body<PokemonListing>()
            Result.Success(result).mapSuccess { nextUrl = it.next; it }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getNextList(): Result<PokemonListing> {
        if (nextUrl == null) {
            return Result.Error(Exception("No More Data to Load"))
        }
        return try {
            val result = api.getNextList(nextUrl.orEmpty()).body<PokemonListing>()
            Result.Success(result).mapSuccess { nextUrl = it.next; it }
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