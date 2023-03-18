package space.rodionov.porosenokpetr.feature_vocabulary.presentation.model

import space.rodionov.porosenokpetr.core.util.Constants

data class CategoryUi(
    val resourceName: String,
    val isCategoryActive: Boolean = true,
    val id: Int? = null,
    val nameRus: String,
    val nameUkr: String,
    val nameEng: String? = null,
    val isOpenedInCollection: Boolean = false
) {

    fun getLocalizedName(lang: Int) = when (lang) {
        Constants.LANGUAGE_RU -> nameRus
        Constants.LANGUAGE_UA -> nameUkr
        Constants.LANGUAGE_EN -> nameEng ?: nameRus
        else -> nameRus
    }
}
