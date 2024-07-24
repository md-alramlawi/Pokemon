package com.pokemon.shared

import database.di.DatabaseModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        val databaseModule = DatabaseModule.invoke()
        modules(commonModules.plus(databaseModule))
    }
}