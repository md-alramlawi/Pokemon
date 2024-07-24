package database.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.PokemonDatabase
import database.datasource.LocalDatasource
import database.datasource.LocalDatasourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

object DatabaseModule {
    operator fun invoke(context: Context): Module = module {
        single<LocalDatasource> { LocalDatasourceImpl(pokemonDatabase(context).pokemonDao()) }
    }
}

private fun pokemonDatabase(context: Context): PokemonDatabase {
    val dbFile = context.getDatabasePath("pokemon.db")
    return Room.databaseBuilder<PokemonDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}
