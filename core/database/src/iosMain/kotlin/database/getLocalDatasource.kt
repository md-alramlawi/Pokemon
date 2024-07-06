package database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.datasource.LocalDatasource
import database.datasource.LocalDatasourceImpl
import platform.Foundation.NSHomeDirectory

fun getLocalDatasource(): LocalDatasource {
    return LocalDatasourceImpl(pokemonDatabase.pokemonDao())
}

private val pokemonDatabase: PokemonDatabase
    get() {
        val dbFile = NSHomeDirectory() + "/pokemon.db"
        return Room.databaseBuilder<PokemonDatabase>(
            name = dbFile,
            factory = { PokemonDatabase::class.instantiateImpl() }
        )
            .setDriver(BundledSQLiteDriver())
            .build()
    }
