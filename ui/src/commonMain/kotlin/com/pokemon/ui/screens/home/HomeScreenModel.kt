package com.pokemon.ui.screens.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.pokemon.common.result.mapError
import com.pokemon.common.result.mapSuccess
import com.pokemon.data.repository.PokemonRepository
import com.pokemon.model.SimplePokemon
import com.pokemon.model.UIEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenModel(
    private val pokemonRepository: PokemonRepository
) : ScreenModel {
    private var _list: MutableStateFlow<List<SimplePokemon>> = MutableStateFlow(emptyList())
    val list = _list.asStateFlow()

    private var _uiEvent: MutableStateFlow<UIEvent> = MutableStateFlow(UIEvent.Idle)
    val uiEvent = _uiEvent.asStateFlow()

    fun getList() {
        screenModelScope.launch {
            _uiEvent.emit(UIEvent.Loading)
            pokemonRepository.getPokemonList()
                .also {
                    _uiEvent.emit(UIEvent.Idle)
                }
                .mapSuccess {
                    _list.emit(it)
                }.mapError {
                    _uiEvent.emit(UIEvent.Failure(it))
                }
        }
    }

    fun resetUIEvent(){
        screenModelScope.launch { _uiEvent.emit(UIEvent.Idle) }
    }
}
