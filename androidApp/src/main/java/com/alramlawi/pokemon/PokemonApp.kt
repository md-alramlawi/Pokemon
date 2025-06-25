package com.alramlawi.pokemon

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import shared.di.initKoin

class PokemonApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(app = this) {
            androidContext(this@PokemonApp)
            androidLogger()
        }
    }
}
