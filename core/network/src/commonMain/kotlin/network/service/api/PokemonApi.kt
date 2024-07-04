package network.service.api

import io.ktor.client.statement.HttpResponse

interface PokemonApi {

    suspend fun getPokemonListing(): HttpResponse
    suspend fun getPokemon(name: String): HttpResponse
}