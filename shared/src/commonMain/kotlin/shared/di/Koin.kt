package shared.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import shared.di.modules.dataModule
import shared.di.modules.viewModelModule

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(dataModule)
        modules(viewModelModule)
    }
}
