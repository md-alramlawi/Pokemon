package shared.di

import core.network.di.networkModule
import data.di.repositoryModule
import database.di.databaseModule
import domain.di.useCaseModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import shared.di.modules.viewModelModule

fun initKoinApplication(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(databaseModule)
        modules(networkModule)
        modules(repositoryModule)
        modules(useCaseModule)
        modules(viewModelModule)
    }
}
