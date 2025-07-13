package shared.di.modules

import feature.detail.DetailViewModel
import feature.favorite.FavoriteViewModel
import feature.home.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelModule: Module = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::FavoriteViewModel)
}
