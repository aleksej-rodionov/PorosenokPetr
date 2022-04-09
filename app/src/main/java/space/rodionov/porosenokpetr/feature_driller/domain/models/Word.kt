package space.rodionov.porosenokpetr.feature_driller.domain.models

import space.rodionov.porosenokpetr.feature_driller.utils.Constants.EMPTY_STRING

data class Word(
    val rus: String,
    val ukr: String?,
    val eng: String,
    val swe: String?,
    val categoryName: String,
    val isWordActive: Boolean
): BaseModel {

    fun getTranslation(lang: Int): String {
        return when (lang) {
            0 -> rus
            1 -> ukr ?: EMPTY_STRING
            2 -> eng
            3 -> swe ?: EMPTY_STRING
            else -> eng
        }
    }
}