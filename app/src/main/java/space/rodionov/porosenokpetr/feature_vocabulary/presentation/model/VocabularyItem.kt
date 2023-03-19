package space.rodionov.porosenokpetr.feature_vocabulary.presentation.model

import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.util.Constants

sealed class VocabularyItem {

    data class CategoryUi(
        val resourceName: String,
        val isCategoryActive: Boolean = true,
        val id: Int? = null,
        val nameRus: String,
        val nameUkr: String,
        val nameEng: String? = null,
        val isDisplayedInCollection: Boolean = false
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





//for testing

val testWord = Word(
    rus = "хуй",
    ukr = "хуй",
    eng = "dick",
    swe = "kuk",
    categoryName = "Shit",
    isWordActive = true
)

val testCategory = Category(
    resourceName = "Shit",
    isCategoryActive = true,
    nameRus = "Дерьмо",
    nameUkr = "Лайно",
    nameEng = "Shit"
)