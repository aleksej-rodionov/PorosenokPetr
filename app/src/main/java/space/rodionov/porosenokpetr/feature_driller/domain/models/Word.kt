package space.rodionov.porosenokpetr.feature_driller.domain.models

data class Word(
    val eng: String,
    val rus: String,
    val ukr: String?,
    val swe: String?,
    val categoryName: String,
    val isWordActive: Boolean
): BaseModel