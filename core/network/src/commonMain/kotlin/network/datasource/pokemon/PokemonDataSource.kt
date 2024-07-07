package network.datasource.pokemon

import common.result.NoMoreException
import common.result.Result
import common.result.mapSuccess
import io.ktor.client.call.body
import kotlinx.coroutines.delay
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
        delay(5_00)
        return try {
            val result = api.getInitialList().body<PokemonListing>()
            Result.Success(result).mapSuccess { nextUrl = it.next; it }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getNextList(): Result<PokemonListing> {
        delay(2_00)
        if (nextUrl == null) {
            return Result.Error(NoMoreException("No More Data to Load"))
        }
        return try {
            val result = api.getNextList(nextUrl.orEmpty()).body<PokemonListing>()
            Result.Success(result).mapSuccess { nextUrl = it.next; it }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getPokemon(name: String): Result<PokemonDto> {
        delay(5_00)
        return try {
            val result = api.getPokemon(name).body<PokemonDto>()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}