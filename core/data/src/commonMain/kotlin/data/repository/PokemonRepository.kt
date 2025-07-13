package data.repository

import common.result.Result
import common.result.mapSuccess
import constant.Constants
import core.network.PokemonDataSource
import data.mapper.toModel
import database.datasource.LocalDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import model.Pokemon
import model.SimplePokemon

interface PokemonRepository {
    suspend fun getPokemonPage(offset: Int?): Result<List<SimplePokemon>>
    suspend fun bookmark(exist: Boolean, simplePokemon: SimplePokemon)
    suspend fun getBookmarks(): Result<List<SimplePokemon>>
    suspend fun getPokemonDetails(name: String): Result<Pokemon>
}

internal class PokemonRepositoryImpl(
    private val remoteDataSource: PokemonDataSource,
    private val localDatasource: LocalDatasource,
) : PokemonRepository {

    private var nextOffset: Int = 0

    override suspend fun getPokemonPage(
        offset: Int?,
    ): Result<List<SimplePokemon>> = withContext(Dispatchers.IO) {
        if (offset != null) nextOffset = offset
        remoteDataSource.getPokemonPage(nextOffset)
            .mapSuccess { response ->
                nextOffset += Constants.PAGE_LIMIT
                response.results.map { dto -> dto.toModel }
            }
    }

    override suspend fun bookmark(
        exist: Boolean,
        simplePokemon: SimplePokemon,
    ) = withContext(Dispatchers.IO) {
        if (exist) {
            localDatasource.delete(simplePokemon)
        } else {
            localDatasource.upsert(simplePokemon)
        }
    }

    override suspend fun getBookmarks(): Result<List<SimplePokemon>> = withContext(Dispatchers.IO) {
        localDatasource.getBookmarks()
    }

    override suspend fun getPokemonDetails(
        name: String,
    ): Result<Pokemon> = withContext(Dispatchers.IO) {
        remoteDataSource.getPokemon(name).mapSuccess { it.toModel }
    }
}
