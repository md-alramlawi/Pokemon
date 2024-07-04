package database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.datasource.LocalDatasource
import database.datasource.LocalDatasourceImpl

fun getLocalDatasource(context: Context): LocalDatasource {
    return LocalDatasourceImpl(pokemonDatabase(context).pokemonDao())
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