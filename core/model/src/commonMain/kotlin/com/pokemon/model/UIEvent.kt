package com.pokemon.model

sealed interface UIEvent {
    data object Idle : UIEvent
    data object Loading : UIEvent
    data class Failure(val message: String) : UIEvent
}