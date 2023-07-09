package space.rodionov.porosenokpetr.feature_cardstack.presentation.model

import space.rodionov.porosenokpetr.core.util.Constants
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.core.util.Language

sealed class CardStackItem {

    data class WordUi(
        val rus: String,
        val ukr: String?,
        val eng: String,
        val swe: String?,
        val categoryName: String,
        val wordStatus: Int = Constants.WORD_ACTIVE,
        val id: Int? = null,
        val nativeLang: Language = Language.Russian,
        val isNativeToForeign: Boolean = false,
        val mode: Int = MODE_LIGHT
    ): CardStackItem() {

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

    data class BannerUi(
        val product: Int = 0
    ): CardStackItem()
}