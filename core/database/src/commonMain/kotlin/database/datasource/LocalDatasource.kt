package database.datasource

import common.result.Result
import database.DataBaseFactory
import database.PokemonDao
import database.mapper.toEntity
import database.mapper.toModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import model.SimplePokemon

interface LocalDatasource {

    suspend fun getBookmarks(): Result<List<SimplePokemon>>

    suspend fun upsert(pokemon: SimplePokemon)

    suspend fun delete(pokemon: SimplePokemon)

    companion object {
        fun create(dataBaseFactory: DataBaseFactory): LocalDatasource {
            val dao = dataBaseFactory.createRoomDatabase().pokemonDao()
            return LocalDatasourceImpl(dao)
        }
    }
}

private class LocalDatasourceImpl(private val dao: PokemonDao) : LocalDatasource {

    override suspend fun getBookmarks(): Result<List<SimplePokemon>> {
        return Result.Success(dao.getPokemonList().first().map { it.toModel })
    }

    override suspend fun upsert(pokemon: SimplePokemon) {
        dao.upsert(pokemon.toEntity)
    }

    override suspend fun delete(pokemon: SimplePokemon) {
        dao.delete(pokemon.toEntity)
    }
}

private class FakeLocalDatasource : LocalDatasource {
    private val pokemonList = MutableStateFlow<List<SimplePokemon>>(emptyList())

    override suspend fun upsert(pokemon: SimplePokemon) {
        pokemonList.update { currentList ->
            val mutableList = currentList.toMutableList()
            val index = mutableList.indexOfFirst { it.id == pokemon.id }
            if (index != -1) {
                mutableList[index] = pokemon
            } else {
                mutableList.add(pokemon)
            }
            mutableList.toList()
        }
    }

    override suspend fun delete(pokemon: SimplePokemon) {
        pokemonList.update { currentList ->
            currentList.filterNot { it.id == pokemon.id }
        }
    }

    override suspend fun getBookmarks(): Result<List<SimplePokemon>> {
        return Result.Success(pokemonList.value)
    }
}
