package space.rodionov.porosenokpetr.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_EN
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_RU
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_UA

@Parcelize
data class Category(
    val name: String,
    val isCategoryActive: Boolean = true,
    val id: Int? = null,
    val nameRus: String,
    val nameUkr: String,
    val nameEng: String? = null
): Parcelable {

    fun getLocalizedName(lang: Int) = when (lang) {
        LANGUAGE_RU -> nameRus
        LANGUAGE_UA -> nameUkr
        LANGUAGE_EN -> nameEng ?: nameRus
        else -> nameRus
    }
}