package database.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.PokemonDatabase
import database.datasource.LocalDatasource
import database.datasource.LocalDatasourceImpl
import database.instantiateImpl
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

object DatabaseModule {
    operator fun invoke(): Module = module {
        single<LocalDatasource> { LocalDatasourceImpl(pokemonDatabase.pokemonDao()) }
    }
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
