package space.rodionov.porosenokpetr.core.util

sealed class Language(val languageCode: String) {
    object Russian : Language(LANGUAGE_RU)
    object Ukrainian : Language(LANGUAGE_UA)
    object English : Language(LANGUAGE_EN)
    object Swedish : Language(LANGUAGE_SE)

    companion object {
        const val LANGUAGE_RU = "ru"
        const val LANGUAGE_UA = "uk"
        const val LANGUAGE_EN = "en"
        const val LANGUAGE_SE = "sv"

        fun resolveLanguage(languageCode: String) {
            when (languageCode) {
                LANGUAGE_RU -> Russian
                LANGUAGE_UA -> Ukrainian
                LANGUAGE_EN -> English
                LANGUAGE_SE -> Swedish
                else -> throw Exception("Couldn't resolve language")
            }
        }
    }
}



