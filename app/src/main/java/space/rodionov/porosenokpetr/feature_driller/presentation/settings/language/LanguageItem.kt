package space.rodionov.porosenokpetr.feature_driller.presentation.settings.language

import android.content.ContentValues.TAG
import android.util.Log
import space.rodionov.porosenokpetr.feature_driller.utils.AppFlavor
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_EN
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_RU
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_SE
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_UA
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_DB_REFACTOR

data class LanguageItem(
    val langIndex: Int,
    val nameRus: String,
    val nameUkr: String,
    val nameEng: String,
    val nameSwe: String
) {

    fun getLocalizedName(lang: Int): String {
        return when (lang) {
            NATIVE_LANGUAGE_RU -> nameRus
            NATIVE_LANGUAGE_UA -> nameUkr
            NATIVE_LANGUAGE_EN -> nameEng
            NATIVE_LANGUAGE_SE -> nameSwe
            else -> nameRus
        }
    }
}

object LanguageHelper {

    val russian = LanguageItem(NATIVE_LANGUAGE_RU, "Русский", "Россiйська", "Russian", "Ryska")
    val ukrainian = LanguageItem(NATIVE_LANGUAGE_UA, "Украинский", "Українська", "Ukrainian", "Ukrainsk")
    val english = LanguageItem(NATIVE_LANGUAGE_EN, "Инглиш", "Iнглiш", "English","Engelska")
    val swedish = LanguageItem(NATIVE_LANGUAGE_SE, "Шведский", "Шведська", "Swedish","Svenska")

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
            NATIVE_LANGUAGE_RU -> russian
            NATIVE_LANGUAGE_UA -> ukrainian
            NATIVE_LANGUAGE_EN -> english
            NATIVE_LANGUAGE_SE -> swedish
            else -> russian
        }
    }
}