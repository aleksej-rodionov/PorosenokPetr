package space.rodionov.porosenokpetr.core.data.remote.model

data class WordDto(
    val catName: String,
    val swe: String?,
    val eng: String,
    val rus: String,
    val ukr: String?,
    val remoteId: String
)