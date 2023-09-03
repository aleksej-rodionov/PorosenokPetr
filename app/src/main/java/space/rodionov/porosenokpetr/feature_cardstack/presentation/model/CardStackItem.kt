package space.rodionov.porosenokpetr.feature_cardstack.presentation.model

import androidx.annotation.DrawableRes
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.util.Constants
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.core.util.Language

sealed class CardStackItem(val viewType: Int) {

    data class WordUi(
        val categoryName: String,
        val rus: String,
        val eng: String,
        val ukr: String?,
        val swe: String?,
        val examples: List<String> = emptyList(),
        val wordStatus: Int = Constants.WORD_ACTIVE,
        val id: Int = 0,
        val nativeLang: Language = Language.Russian,
        val isNativeToForeign: Boolean = false,
        val learnedLanguage: Language = Language.Swedish,
        val mode: Int = MODE_LIGHT
    ) : CardStackItem(VIEW_TYPE_WORD) {

        override fun isItemEqual(other: CardStackItem): Boolean {
            return other is WordUi && this.id == other.id
        }

        override fun isContentEqual(other: CardStackItem): Boolean {
            return other is WordUi &&
                    this.nativeLang == other.nativeLang &&
                    this.isNativeToForeign == other.isNativeToForeign &&
                    this.mode == other.mode
        }

        override fun getPayloadDiff(other: CardStackItem): MutableList<Any> {
            val payloads = mutableListOf<Any>()
            if (other !is WordUi) return payloads

            if (this.nativeLang != other.nativeLang) {
                payloads.add(NATIVE_LANG_PAYLOAD)
            }
            if (this.isNativeToForeign != other.isNativeToForeign) {
                payloads.add(IS_NATIVE_TO_FOREIGN_PAYLOAD)
            }
            if (this.mode != other.mode) {
                payloads.add(MODE_PAYLOAD)
            }
            return payloads
        }

        fun getTranslation(lang: Language): String {
            return when (lang) {
                Language.Russian -> rus
                Language.Ukrainian -> ukr ?: "<no ukrainian translation>"
                Language.English -> eng
                Language.Swedish -> swe ?: "<no swedish translation>"
            }
        }

        companion object {
            const val NATIVE_LANG_PAYLOAD = "nativeLang"
            const val IS_NATIVE_TO_FOREIGN_PAYLOAD = "isNativeToForeign"
            const val MODE_PAYLOAD = "mode"
        }
    }

    data class BannerUi(
        val text: String = "Давай шабить?",
        @DrawableRes val imageRes: Int = R.drawable.img_bong
    ) : CardStackItem(VIEW_TYPE_BANNER) {

        override fun isItemEqual(other: CardStackItem): Boolean {
            return other is BannerUi && this.text == other.text
        }
    }

    /**
     * Функция сравнивает ответы (один и тот же ответ, или два разных)
     */
    open fun isItemEqual(other: CardStackItem): Boolean {
        return this.hashCode() == other.hashCode()
    }

    /**
     * Функция проверяет неизменность изменяемых параметров одного и того же ответа
     */
    open fun isContentEqual(other: CardStackItem): Boolean {
        return this == other
    }

    /**
     * Функция проверяет, какие изменяемые параметры одного и того же ответа изменились,
     * и возвращает их список (в виде списка текстовых обозначений)
     */
    open fun getPayloadDiff(other: CardStackItem): MutableList<Any> {
        return mutableListOf()
    }

    companion object {
        const val VIEW_TYPE_WORD = 0
        const val VIEW_TYPE_BANNER = 1
    }
}