package database.datasource

import database.PokemonDao
import database.PokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

private val SimplePokemon.toEntity: PokemonEntity
    get() = PokemonEntity(
        id = this.id,
        name = this.name,
        url = this.url
    )

private val PokemonEntity.toModel: SimplePokemon
    get() = SimplePokemon(
        id = this.id,
        name = this.name,
        url = this.url
    )