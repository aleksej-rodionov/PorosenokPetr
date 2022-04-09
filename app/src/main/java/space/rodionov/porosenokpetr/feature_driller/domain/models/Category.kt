package space.rodionov.porosenokpetr.feature_driller.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_EN
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_RU
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_UA

@Parcelize
data class Category(
    val resourceName: String,
    val isCategoryActive: Boolean = true,
    val nameRus: String,
    val nameUkr: String,
    val nameEng: String? = null
): BaseModel, Parcelable {

    fun getLocalizedName(lang: Int) = when (lang) {
        NATIVE_LANGUAGE_RU -> nameRus
        NATIVE_LANGUAGE_UA -> nameUkr
        NATIVE_LANGUAGE_EN -> nameEng ?: nameRus
        else -> nameRus
    }
}