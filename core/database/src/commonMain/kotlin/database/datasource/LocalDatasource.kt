package database.datasource

import database.PokemonDao
import database.mapper.toEntity
import database.mapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import model.SimplePokemon

interface LocalDatasource {
    suspend fun upsert(pokemon: SimplePokemon)
    suspend fun delete(pokemon: SimplePokemon)
    fun getAll(): Flow<List<SimplePokemon>>
}

class LocalDatasourceImpl(private val dao: PokemonDao) : LocalDatasource {
    override suspend fun upsert(pokemon: SimplePokemon) {
        dao.upsert(pokemon.toEntity)
    }

    override suspend fun delete(pokemon: SimplePokemon) {
        dao.delete(pokemon.toEntity)
    }

    override fun getAll(): Flow<List<SimplePokemon>> {
        return dao.getPokemonList().map { entities -> entities.map { it.toModel } }
    }
}


class FakeLocalDatasource : LocalDatasource {
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

    override fun getAll(): Flow<List<SimplePokemon>> {
        return pokemonList.asStateFlow()
    }
}