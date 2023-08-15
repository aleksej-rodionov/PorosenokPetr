package space.rodionov.porosenokpetr.feature_vocabulary.presentation.model

import space.rodionov.porosenokpetr.core.util.Constants
import space.rodionov.porosenokpetr.core.util.Constants.WORD_ACTIVE
import space.rodionov.porosenokpetr.core.util.Language

sealed class VocabularyItem {

    data class CategoryUi(
        val name: String,
        val isCategoryActive: Boolean = true,
        val learnedFromActivePercentage: Int = 0,
        val id: Int = 0,
        val nameRus: String,
        val nameUkr: String,
        val nameEng: String? = null,
        val isExpanded: Boolean = false,
        val words: List<WordUi> = emptyList(),
        val isFocusedInList: Boolean = false,
        val isTurnedOn: Boolean = false
    ) : VocabularyItem() {

        fun getLocalizedName(lang: Language) = when (lang) {
            Language.Russian -> nameRus
            Language.Ukrainian -> nameUkr
            Language.English -> nameEng ?: nameRus
            else -> nameRus
        }
    }

    data class WordUi(
        val rus: String,
        val ukr: String?,
        val eng: String,
        val swe: String?,
        val categoryName: String,
        val wordStatus: Int = WORD_ACTIVE,
        val id: Int = 0
    ) : VocabularyItem() {

        fun getTranslation(lang: Language): String {
            return when (lang) {
                Language.Russian -> rus
                Language.Ukrainian -> ukr ?: Constants.EMPTY_STRING
                Language.English -> eng
                Language.Swedish -> swe ?: Constants.EMPTY_STRING
            }
        }
    }
}