package com.pokemon.common.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

// Define the ApplicationScope qualifier
val ApplicationScope = named("ApplicationScope")

enum class AppDispatchers(val raw: String) {
    Default("DefaultDispatcher"),
    IO("IoDispatcher")
}

object CoroutineModule {
    operator fun invoke() = module {
        single(named(AppDispatchers.IO.raw)) { Dispatchers.IO }
        single(named(AppDispatchers.Default.raw)) { Dispatchers.Default }

        single(ApplicationScope) {
            CoroutineScope(SupervisorJob() + get<CoroutineDispatcher>(named(AppDispatchers.Default)))
        }
    }
}