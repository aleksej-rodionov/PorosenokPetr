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
        val learnedLanguage: Language = Language.Swedish,
        val mode: Int = MODE_LIGHT
    ): CardStackItem() {

        fun getTranslation(lang: Language): String {
            return when (lang) {
                Language.Russian -> rus
                Language.Ukrainian -> ukr ?: "<no ukrainian translation>"
                Language.English -> eng
                Language.Swedish -> swe ?: "<no swedish translation>"
            }
        }
    }

    data class BannerUi( //todo other view type
        val product: Int = 0
    ): CardStackItem()
}