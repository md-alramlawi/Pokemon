package com.alramlawi.pokemon.android

import android.app.Application
import com.pokemon.shared.initKoin

class PokemonApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
