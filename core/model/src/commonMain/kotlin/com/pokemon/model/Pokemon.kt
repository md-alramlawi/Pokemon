package com.pokemon.model

data class Pokemon(
    val name: String = "",
    val imageUrl: String = "",
    val types: List<String> = emptyList(),
    val weight: Int = 0,
    val height: Int = 0,
)