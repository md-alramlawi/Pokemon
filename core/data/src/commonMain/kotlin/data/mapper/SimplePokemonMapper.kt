package data.mapper

import model.SimplePokemon
import core.network.PokemonListing
import core.network.util.ApiConstant

val PokemonListing.Pokemon.toModel: SimplePokemon
    get() {
        val id = url.split("/").let { it[it.size - 2] }
        return SimplePokemon(
            id = id,
            name = name,
            url = "${ApiConstant.ICON_BASE_URL}$id.png"
        )
    }