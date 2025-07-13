package com.alramlawi.pokemon

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import shared.di.initKoinApplication

class PokemonApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoinApplication {
            androidContext(this@PokemonApp)
            androidLogger()
        }
    }
}
