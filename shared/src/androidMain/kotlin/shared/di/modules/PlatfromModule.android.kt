package shared.di.modules

import android.app.Application
import database.DataBaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun getPlatformModule(app: Any?): Module = module {
    app as Application
    factory { DataBaseFactory(app) }
}
