package space.rodionov.porosenokpetr.core.util

import androidx.annotation.StringRes
import space.rodionov.porosenokpetr.R

sealed class Language(
    val languageTag: String,
    @StringRes val languageNameRes: Int
) {
    object Russian : Language(
        LANGUAGE_RU,
        R.string.russian
    )
    object Ukrainian : Language(
        LANGUAGE_UA,
        R.string.ukrainian
    )
    object English : Language(
        LANGUAGE_EN,
        R.string.english
    )
    object Swedish : Language(
        LANGUAGE_SE,
        R.string.swedish
    )

    companion object {
        const val LANGUAGE_RU = "ru"
        const val LANGUAGE_UA = "uk"
        const val LANGUAGE_EN = "en"
        const val LANGUAGE_SE = "sv"

        fun resolveLanguage(languageCode: String): Language {
            return when (languageCode) {
                LANGUAGE_RU -> Russian
                LANGUAGE_UA -> Ukrainian
                LANGUAGE_EN -> English
                LANGUAGE_SE -> Swedish
                else -> throw Exception("Couldn't resolve language")
            }
        }
    }
}



