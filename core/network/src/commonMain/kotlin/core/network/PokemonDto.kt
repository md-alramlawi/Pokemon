package core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto(
    val abilities: List<Ability>,
    @SerialName("base_experience")
    val baseExperience: Int,
    val forms: List<Form>,
    @SerialName("game_indices")
    val gameIndices: List<GameIndice>,
    val height: Double,
    val id: Int,
    @SerialName("is_default")
    val isDefault: Boolean,
    @SerialName("location_area_encounters")
    val locationAreaEncounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Double,
) {
    @Serializable
    data class Ability(
        val ability: Ability,
        @SerialName("is_hidden")
        val isHidden: Boolean,
        val slot: Int,
    ) {
        @Serializable
        data class Ability(
            val name: String,
            val url: String,
        )
    }

    @Serializable
    data class Form(
        val name: String,
        val url: String,
    )

    @Serializable
    data class GameIndice(
        @SerialName("game_index")
        val gameIndex: Int,
        val version: Version,
    ) {
        @Serializable
        data class Version(
            val name: String,
            val url: String,
        )
    }

    @Serializable
    data class Move(
        val move: Move,
        @SerialName("version_group_details")
        val versionGroupDetails: List<VersionGroupDetail>,
    ) {
        @Serializable
        data class Move(
            val name: String,
            val url: String,
        )

        @Serializable
        data class VersionGroupDetail(
            @SerialName("level_learned_at")
            val levelLearnedAt: Int,
            @SerialName("move_learn_method")
            val moveLearnMethod: MoveLearnMethod,
            @SerialName("version_group")
            val versionGroup: VersionGroup,
        ) {
            @Serializable
            data class MoveLearnMethod(
                val name: String,
                val url: String,
            )

            @Serializable
            data class VersionGroup(
                val name: String,
                val url: String,
            )
        }
    }

    @Serializable
    data class Species(
        val name: String,
        val url: String,
    )

    @Serializable
    data class Sprites(
        @SerialName("back_default")
        val backDefault: String?,
        @SerialName("back_female")
        val backFemale: String?,
        @SerialName("back_shiny")
        val backShiny: String?,
        @SerialName("back_shiny_female")
        val backShinyFemale: String?,
        @SerialName("front_default")
        val frontDefault: String?,
        @SerialName("front_female")
        val frontFemale: String?,
        @SerialName("front_shiny")
        val frontShiny: String?,
        @SerialName("front_shiny_female")
        val frontShinyFemale: String?,
        val other: Other?,
    ) {
        @Serializable
        data class Other(
            val home: Home,
        ) {
            @Serializable
            data class Home(
                @SerialName("front_default")
                val frontDefault: String?,
                @SerialName("front_female")
                val frontFemale: String?,
                @SerialName("front_shiny")
                val frontShiny: String?,
                @SerialName("front_shiny_female")
                val frontShinyFemale: String?,
            )
        }
    }

    @Serializable
    data class Stat(
        @SerialName("base_stat")
        val baseStat: Int,
        val effort: Int,
        val stat: Stat,
    ) {
        @Serializable
        data class Stat(
            val name: String,
            val url: String,
        )
    }

    @Serializable
    data class Type(
        val slot: Int,
        val type: Type,
    ) {
        @Serializable
        data class Type(
            val name: String,
            val url: String,
        )
    }
}
