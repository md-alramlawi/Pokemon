package com.pokemon.ui.screens.detail

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.pokemon.common.result.mapSuccess
import com.pokemon.data.repository.PokemonRepository
import com.pokemon.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailScreenModel(
    private val pokemonRepository: PokemonRepository
) : ScreenModel {

    private var _data: MutableStateFlow<Pokemon?> = MutableStateFlow(null)
    val data = _data.asStateFlow()

    fun getDetails(name: String) {
        screenModelScope.launch {
            pokemonRepository.getPokemon(name).mapSuccess {
                _data.emit(it)
            }
        }
    }
}