package model

data class Pokemon(
    val id: String,
    val name: String,
    val imageUrl: String,
    val iconUrl: String,
    val types: List<String>,
    val stats: List<Stat>,
    val weight: Double,
    val height: Double,
    val isBookmarked: Boolean = false
) {
    data class Stat(
        val name: String,
        val baseStat: Int,
        val effort: Int,
        val percentage: Float,
    )
}