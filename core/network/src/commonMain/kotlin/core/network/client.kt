package core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal fun ktorClient(): HttpClient = HttpClient {
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
