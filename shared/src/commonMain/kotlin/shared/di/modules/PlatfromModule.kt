package shared.di.modules

import org.koin.core.module.Module

internal expect fun getPlatformModule(app: Any?): Module
