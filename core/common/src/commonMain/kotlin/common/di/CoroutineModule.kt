package common.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

// Define the ApplicationScope qualifier
val ApplicationScope = named("ApplicationScope")

enum class AppDispatchers {
    Default,
    IO
}

object CoroutineModule {
    operator fun invoke() = module {
        single(named(AppDispatchers.IO)) { Dispatchers.IO }
        single(named(AppDispatchers.Default)) { Dispatchers.Default }

        single(ApplicationScope) {
            CoroutineScope(SupervisorJob() + get<CoroutineDispatcher>(named(AppDispatchers.Default)))
        }
    }
}