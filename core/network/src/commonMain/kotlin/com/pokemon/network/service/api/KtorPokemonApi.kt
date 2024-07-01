package com.pokemon.network.service.api

import com.pokemon.network.util.ApiConstant
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorPokemonApi(private val client: HttpClient) : PokemonApi {

    override suspend fun getPokemonListing(): HttpResponse {
        val endPoint = ApiConstant.BASE_URL + "pokemon/"
        return client.get(endPoint)
    }

    override suspend fun getPokemon(name: String): HttpResponse {
        val endPoint = ApiConstant.BASE_URL + "pokemon/$name"
        return client.get(endPoint)
    }
}

val client: HttpClient
    get() {
        val json = Json { ignoreUnknownKeys = true }
        return HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Application.Json)
            }
        }
    }


