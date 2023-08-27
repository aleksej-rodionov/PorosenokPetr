package space.rodionov.porosenokpetr.core.data.remote.model

data class WordDto(
    val catName: String,
    val rus: String,
    val eng: String,
    val ukr: String? = null,
    val swe: String? = null,
    val examples: List<String> = emptyList()
) {

    constructor(): this(
        "",
        "",
        "",
        null,
        null,
        emptyList()
    )
}