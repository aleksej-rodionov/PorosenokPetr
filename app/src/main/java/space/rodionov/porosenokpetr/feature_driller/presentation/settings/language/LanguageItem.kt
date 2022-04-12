package space.rodionov.porosenokpetr.feature_driller.presentation.settings.language

import space.rodionov.porosenokpetr.feature_driller.utils.AppFlavor
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_EN
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_RU
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_SE
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_UA

data class LanguageItem(
    val langIndex: Int,
    val nameRus: String,
    val nameUkr: String,
    val nameEng: String,
    val nameSwe: String
) {

    fun getLocalizedName(lang: Int): String {
        return when (lang) {
            LANGUAGE_RU -> nameRus
            LANGUAGE_UA -> nameUkr
            LANGUAGE_EN -> nameEng
            LANGUAGE_SE -> nameSwe
            else -> nameRus
        }
    }
}

object LanguageHelper {

    val russian = LanguageItem(LANGUAGE_RU, "Русский", "Россiйська", "Russian", "Ryska")
    val ukrainian = LanguageItem(LANGUAGE_UA, "Украинский", "Українська", "Ukrainian", "Ukrainsk")
    val english = LanguageItem(LANGUAGE_EN, "Инглиш", "Iнглiш", "English","Engelska")
    val swedish = LanguageItem(LANGUAGE_SE, "Шведский", "Шведська", "Swedish","Svenska")

    fun getNativeLanguages(app: AppFlavor) : MutableList<LanguageItem>{
       return when (app) {
            AppFlavor.ENGLISH_DRILLER -> mutableListOf(russian, ukrainian)
            AppFlavor.SWEDISH_DRILLER -> mutableListOf(russian, ukrainian, english)
        }
    }

    fun getLearnedLanguages(app: AppFlavor) : MutableList<LanguageItem> {
//        Log.d(TAG_DB_REFACTOR, "getLearnedLanguages: ")
        return when (app) {
            AppFlavor.ENGLISH_DRILLER -> mutableListOf(english)
            AppFlavor.SWEDISH_DRILLER -> mutableListOf(ukrainian, english, swedish)
        }
    }

    fun getLangByIndex(langIndex: Int) : LanguageItem {
        return when (langIndex) {
            LANGUAGE_RU -> russian
            LANGUAGE_UA -> ukrainian
            LANGUAGE_EN -> english
            LANGUAGE_SE -> swedish
            else -> russian
        }
    }
}