package com.pokemon.model

data class SimplePokemon(
    val name: String,
    val url: String
){
    val id: String get() = url.split("/").run { this[size - 2] }
}