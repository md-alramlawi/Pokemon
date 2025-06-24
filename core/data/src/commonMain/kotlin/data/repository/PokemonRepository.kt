package data.repository

import common.result.NoMoreException
import common.result.Result
import common.result.mapError
import common.result.mapSuccess
import data.mapper.toModel
import database.datasource.LocalDatasource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import model.Pokemon
import model.SimplePokemon
import core.network.PokemonDataSource
import core.network.PokemonDto

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

    private val detailsMap: LinkedHashMap<String, PokemonDto> = linkedMapOf()
    private val pokemonHashMap: LinkedHashMap<String, SimplePokemon> = linkedMapOf()
    private var loadTotalItems = false
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
        if (pokemonHashMap.isEmpty() || loadTotalItems) {
            return Result.Success(Unit)
        }

        return remoteDataSource.getNextList()
            .mapError { exc ->
                loadTotalItems = exc is NoMoreException
            }
            .mapSuccess { listing ->
                listing.results.map { dto -> dto.toModel }.also { list ->
                    pokemonHashMap.putAll(list.associateBy { it.id })
                    currentList.emitFilteredList(pokemonHashMap.values.toList(), searchQuery.value)
                }
            }
    }

    private suspend fun getApiPokemonList(): Result<List<SimplePokemon>> {
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
        val bookmarks = localDatasource.getAll().first().map { it.id }
        val detailsResult = detailsMap[name]?.let {
            Result.Success(it)
        } ?: remoteDataSource.getPokemon(name = name)
        return detailsResult.mapSuccess { dto ->
            detailsMap[name] = dto
            dto.toModel.copy(isBookmarked = bookmarks.contains(dto.id.toString()))
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