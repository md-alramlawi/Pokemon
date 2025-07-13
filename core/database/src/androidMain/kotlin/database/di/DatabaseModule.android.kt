package database.di

import database.DataBaseFactory
import database.datasource.LocalDatasource
import org.koin.core.module.Module
import org.koin.dsl.module

actual val databaseModule: Module = module {
    factory { DataBaseFactory(get()) }
    single<LocalDatasource> { LocalDatasource.create(dataBaseFactory = get()) }
}
