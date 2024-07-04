package data.repository

import common.result.Result
import common.result.mapSuccess
import data.mapper.toModel
import database.datasource.LocalDatasource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import model.Pokemon
import model.SimplePokemon
import network.datasource.pokemon.PokemonDataSource

interface PokemonRepository {
    val currentList: StateFlow<List<SimplePokemon>>
    val currentId: StateFlow<String>
    val searchQuery: StateFlow<String>
    suspend fun setCurrent(id: String): Result<Unit>
    suspend fun getPokemonList(): Result<Unit>
    suspend fun search(query: String)
    suspend fun getPokemon(id: String): Result<Pokemon>

    fun getBookmarks(): Flow<List<SimplePokemon>>
    suspend fun bookmark(id: String): Result<Unit>
}

class PokemonRepositoryImpl(
    private val remoteDataSource: PokemonDataSource,
    private val localDatasource: LocalDatasource
) : PokemonRepository {

    private val pokemonHashMap: LinkedHashMap<String, SimplePokemon> = linkedMapOf()
    override val currentList = MutableStateFlow<List<SimplePokemon>>(emptyList())
    override val currentId = MutableStateFlow("")
    override val searchQuery = MutableStateFlow("")

    override suspend fun setCurrent(id: String): Result<Unit> {
        return pokemonHashMap[id]?.let {
            currentId.emit(it.id)
            Result.Success(Unit)
        } ?: Result.Error(Exception("Not found"))
    }

    override suspend fun getPokemonList(): Result<Unit> {
        val result = if (pokemonHashMap.isNotEmpty()) {
            Result.Success(pokemonHashMap.values)
        } else {
            getApiPokemonList()
        }
        return result.mapSuccess { currentList.emit(it.toList()) }
    }

    private suspend fun getApiPokemonList(): Result<List<SimplePokemon>> {
        delay(1_000)
        return remoteDataSource.getPokemonListing().mapSuccess { listing ->
            listing.results.map { dto -> dto.toModel }.also { list ->
                pokemonHashMap.clear()
                pokemonHashMap.putAll(list.associateBy { it.id })
            }
        }
    }


    override suspend fun search(query: String) {
        searchQuery.update { query }
        val filteredList = pokemonHashMap.values.filter { it.name.contains(query, true) }
        currentList.update { filteredList }
    }

    override suspend fun getPokemon(id: String): Result<Pokemon> {
        delay(1_000)
        val bookmarks = localDatasource.getAll().first().map { it.id }
        return pokemonHashMap[id]?.let {
            remoteDataSource.getPokemon(name = it.name).mapSuccess { dto ->
                dto.toModel.copy(isFavorite = bookmarks.contains(id))
            }
        } ?: Result.Error(Exception("Not found"))
    }

    override fun getBookmarks(): Flow<List<SimplePokemon>> {
        return localDatasource.getAll()
    }

    override suspend fun bookmark(id: String): Result<Unit> {
        return pokemonHashMap[id]?.let {
            val bookmarks = localDatasource.getAll().first()
            if (bookmarks.contains(it)) {
                localDatasource.delete(it)
            } else {
                localDatasource.upsert(it)
            }
            Result.Success(Unit)
        } ?: Result.Error(Exception("Not found"))
    }
}