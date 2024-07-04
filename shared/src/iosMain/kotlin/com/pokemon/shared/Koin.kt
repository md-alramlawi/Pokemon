package com.pokemon.shared

import database.datasource.LocalDatasource
import database.getLocalDatasource
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() {
    val localDatasourceModule = module {
        single<LocalDatasource> { getLocalDatasource() }
    }
    startKoin {
        modules(commonModules.plus(localDatasourceModule))
    }
}