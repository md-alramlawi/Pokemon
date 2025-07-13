package core.network

import constant.Constants
import core.network.util.ApiConstant
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal interface PokemonApi {
    suspend fun getInitialList(): HttpResponse

    suspend fun getNextList(nextUrl: String): HttpResponse

    suspend fun getPage(offset: Int): HttpResponse

    suspend fun getPokemon(name: String): HttpResponse
}

internal class KtorPokemonApi(private val client: HttpClient) : PokemonApi {
    override suspend fun getInitialList(): HttpResponse {
        val endPoint = ApiConstant.BASE_URL + "pokemon/"
        return client.get(endPoint)
    }

    override suspend fun getNextList(nextUrl: String): HttpResponse = client.get(nextUrl)

    override suspend fun getPage(offset: Int): HttpResponse {
        val endPoint = ApiConstant.BASE_URL + "pokemon/?offset=$offset&limit=${Constants.PAGE_LIMIT}"
        return client.get(endPoint)
    }

    override suspend fun getPokemon(name: String): HttpResponse {
        val endPoint = ApiConstant.BASE_URL + "pokemon/$name"
        return client.get(endPoint)
    }
}

internal val client = HttpClient {
    install(plugin = ContentNegotiation) {
        json(
            json = Json {
                ignoreUnknownKeys = true
                isLenient = true
            },
            contentType = ContentType.Application.Json,
        )
    }
    install(plugin = HttpTimeout) {
        requestTimeoutMillis = 15_000
        connectTimeoutMillis = 10_000
        socketTimeoutMillis = 15_000
    }
    install(plugin = Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
}
