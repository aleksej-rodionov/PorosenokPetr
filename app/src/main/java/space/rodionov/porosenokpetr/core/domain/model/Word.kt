package space.rodionov.porosenokpetr.core.domain.model

import space.rodionov.porosenokpetr.core.util.Constants.EMPTY_STRING
import space.rodionov.porosenokpetr.core.util.Constants.WORD_ACTIVE

data class Word(
    val rus: String,
    val ukr: String?,
    val eng: String,
    val swe: String?,
    val categoryName: String,
    val wordStatus: Int = WORD_ACTIVE,
    val id: Int? = null
) {

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