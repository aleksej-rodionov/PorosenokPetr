package space.rodionov.porosenokpetr.feature_wordeditor.presentation.model

import space.rodionov.porosenokpetr.core.util.Constants

data class Translation(
    val language: Int,
    val translation: String
)

data class WordUi(
    val rus: String,
    val ukr: String?,
    val eng: String,
    val swe: String?,
    val categoryName: String,
    val wordStatus: Int = Constants.WORD_ACTIVE,
    val id: Int? = null
){

    fun getTranslation(lang: Int): String {
        return when (lang) {
            0 -> rus
            1 -> ukr ?: Constants.EMPTY_STRING
            2 -> eng
            3 -> swe ?: Constants.EMPTY_STRING
            else -> eng
        }
    }
}