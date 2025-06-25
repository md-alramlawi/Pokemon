package data.mapper

import core.network.PokemonListingResponse
import core.network.util.ApiConstant
import model.SimplePokemon

val PokemonListingResponse.Pokemon.toModel: SimplePokemon
    get() {
        val id = url.split("/").let { it[it.size - 2] }
        return SimplePokemon(
            id = id,
            name = name,
            url = "${ApiConstant.ICON_BASE_URL}$id.png",
        )
    }
