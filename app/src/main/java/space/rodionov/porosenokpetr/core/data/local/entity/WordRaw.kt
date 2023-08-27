package space.rodionov.porosenokpetr.core.data.local.entity

data class WordRaw(
    val catName: String,
    val eng: String,
    val rus: String,
    val ukr: String? = null,
    val swe: String? = null,
    val examples: List<String> = emptyList()
)