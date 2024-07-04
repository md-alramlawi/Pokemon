package com.pokemon.shared

import android.content.Context
import database.datasource.LocalDatasource
import database.getLocalDatasource
import org.koin.core.context.startKoin
import org.koin.dsl.module


fun initKoin(context: Context) {
    val localDatasourceModule = module {
        single<LocalDatasource> { getLocalDatasource(context)}
    }
    startKoin {
        modules(commonModules.plus(localDatasourceModule))
    }
}