package shared.di

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import shared.di.modules.dataModule
import shared.di.modules.domainModule
import shared.di.modules.getPlatformModule
import shared.di.modules.viewModelModule

fun initKoin(app: Application, config: KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(getPlatformModule(app = app))
        modules(dataModule)
        modules(domainModule)
        modules(viewModelModule)
    }
}
