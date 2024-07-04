package database.mapper

import database.PokemonEntity
import model.SimplePokemon

internal val SimplePokemon.toEntity: PokemonEntity
    get() = PokemonEntity(
        id = this.id,
        name = this.name,
        url = this.url
    )

internal val PokemonEntity.toModel: SimplePokemon
    get() = SimplePokemon(
        id = this.id,
        name = this.name,
        url = this.url
    )