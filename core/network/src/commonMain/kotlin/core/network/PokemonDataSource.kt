package core.network

import common.result.Result
import io.ktor.client.call.body
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.coroutineContext

interface PokemonDataSource {

    suspend fun getPokemonPage(offset: Int): Result<PokemonListingResponse>

    suspend fun getPokemon(name: String): Result<PokemonDto>

    companion object {
        fun create(): PokemonDataSource {
            return PokemonDataSourceImpl(PokemonApi.create())
        }
    }
}

private class PokemonDataSourceImpl(private val api: PokemonApi) : PokemonDataSource {

    override suspend fun getPokemonPage(offset: Int): Result<PokemonListingResponse> {
        delay(500)
        return safeApiCall {
            api.getPage(offset).body<PokemonListingResponse>()
        }
    }

    override suspend fun getPokemon(name: String): Result<PokemonDto> {
        delay(500)
        return safeApiCall {
            api.getPokemon(name).body<PokemonDto>()
        }
    }

    private suspend inline fun <T> safeApiCall(apiCall: () -> T?): Result<T> {
        return try {
            coroutineContext.ensureActive() // Check for coroutine cancellation
            val result = apiCall()
            if (result != null) {
                Result.Success(result)
            } else {
                Result.Error(NullPointerException("Response body is null"))
            }
        } catch (e: CancellationException) {
            throw e // Propagate the cancellation exception
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
