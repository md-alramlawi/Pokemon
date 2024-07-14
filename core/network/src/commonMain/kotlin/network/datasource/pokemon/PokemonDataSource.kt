package network.datasource.pokemon

import common.result.NoMoreException
import common.result.Result
import io.ktor.client.call.body
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import network.service.api.PokemonApi
import network.service.dto.PokemonDto
import network.service.response.PokemonListing
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.coroutineContext

interface PokemonDataSource {

    suspend fun getPokemonListing(): Result<PokemonListing>
    suspend fun getNextList(): Result<PokemonListing>
    suspend fun getPokemon(name: String): Result<PokemonDto>
}

class PokemonDataSourceImpl(private val api: PokemonApi) : PokemonDataSource {
    private var nextUrl: String? = null

    override suspend fun getPokemonListing(): Result<PokemonListing> {
        delay(500)
        return safeApiCall {
            val result = api.getInitialList().body<PokemonListing>()
            nextUrl = result.next
            result
        }
    }

    override suspend fun getNextList(): Result<PokemonListing> {
        delay(200)
        if (nextUrl == null) {
            return Result.Error(NoMoreException("No More Data to Load"))
        }
        return safeApiCall {
            val result = api.getNextList(nextUrl.orEmpty()).body<PokemonListing>()
            nextUrl = result.next
            result
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