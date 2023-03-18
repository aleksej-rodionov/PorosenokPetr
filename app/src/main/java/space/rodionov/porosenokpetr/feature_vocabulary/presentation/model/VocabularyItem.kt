package space.rodionov.porosenokpetr.feature_vocabulary.presentation.model

import space.rodionov.porosenokpetr.core.util.Constants

sealed class VocabularyItem {

    data class CategoryUi(
        val resourceName: String,
        val isCategoryActive: Boolean = true,
        val id: Int? = null,
        val nameRus: String,
        val nameUkr: String,
        val nameEng: String? = null,
        val isOpenedInCollection: Boolean = false
    ) : VocabularyItem() {

        fun getLocalizedName(lang: Int) = when (lang) {
            Constants.LANGUAGE_RU -> nameRus
            Constants.LANGUAGE_UA -> nameUkr
            Constants.LANGUAGE_EN -> nameEng ?: nameRus
            else -> nameRus
        }
    }

    data class WordUi(
        val rus: String,
        val ukr: String?,
        val eng: String,
        val swe: String?,
        val categoryName: String,
        val isWordActive: Boolean = true,
        val id: Int? = null
    ): VocabularyItem() {

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
}

