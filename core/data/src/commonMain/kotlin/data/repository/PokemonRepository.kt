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
    val currentName: StateFlow<String>
    val searchQuery: StateFlow<String>
    suspend fun setCurrent(name: String)
    suspend fun getPokemonList(): Result<Unit>
    suspend fun loadNext(): Result<Unit>
    suspend fun search(query: String)
    suspend fun getPokemon(name: String): Result<Pokemon>

    fun getBookmarks(): Flow<List<SimplePokemon>>
    suspend fun bookmark(simplePokemon: SimplePokemon): Result<Unit>
}

class PokemonRepositoryImpl(
    private val remoteDataSource: PokemonDataSource,
    private val localDatasource: LocalDatasource
) : PokemonRepository {

    private val pokemonHashMap: LinkedHashMap<String, SimplePokemon> = linkedMapOf()
    override val currentList = MutableStateFlow<List<SimplePokemon>>(emptyList())
    override val currentName = MutableStateFlow("")
    override val searchQuery = MutableStateFlow("")

    override suspend fun setCurrent(name: String) {
        currentName.emit(name)
    }

    override suspend fun getPokemonList(): Result<Unit> {
        val result = if (pokemonHashMap.isNotEmpty()) {
            Result.Success(pokemonHashMap.values)
        } else {
            getApiPokemonList()
        }
        return result.mapSuccess { currentList.emitFilteredList(it.toList(), searchQuery.value) }
    }

    override suspend fun loadNext(): Result<Unit> {
        delay(1_000)
        return remoteDataSource.getNextList().mapSuccess { listing ->
            listing.results.map { dto -> dto.toModel }.also { list ->
                pokemonHashMap.putAll(list.associateBy { it.id })
                currentList.emitFilteredList(pokemonHashMap.values.toList(), searchQuery.value)
            }
        }
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
        currentList.emitFilteredList(pokemonHashMap.values.toList(), query)
    }

    override suspend fun getPokemon(name: String): Result<Pokemon> {
        delay(1_000)
        val bookmarks = localDatasource.getAll().first().map { it.id }
        return remoteDataSource.getPokemon(name = name).mapSuccess { dto ->
            dto.toModel.copy(isFavorite = bookmarks.contains(dto.id.toString()))
        }
    }

    override fun getBookmarks(): Flow<List<SimplePokemon>> {
        return localDatasource.getAll()
    }

    override suspend fun bookmark(simplePokemon: SimplePokemon): Result<Unit> {
        val bookmarks = localDatasource.getAll().first()
        if (bookmarks.contains(simplePokemon)) {
            localDatasource.delete(simplePokemon)
        } else {
            localDatasource.upsert(simplePokemon)
        }
        return Result.Success(Unit)
    }
}

private suspend fun MutableStateFlow<List<SimplePokemon>>.emitFilteredList(
    list: List<SimplePokemon>,
    query: String
) {
    this.emit(list.filter { it.name.contains(query, true) || it.id == query })
}