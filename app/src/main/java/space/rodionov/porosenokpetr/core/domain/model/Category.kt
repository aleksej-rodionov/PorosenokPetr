package space.rodionov.porosenokpetr.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import space.rodionov.porosenokpetr.core.util.Language

@Parcelize
data class Category(
    val name: String,
    val isCategoryActive: Boolean = true,
    val learnedFromActivePercentage: Int = 0,
    val id: Int? = null,
    val nameRus: String,
    val nameUkr: String? = null,
    val nameEng: String? = null
): Parcelable {

    fun getLocalizedName(lang: Language) = when (lang) {
        Language.Russian -> nameRus
        Language.Ukrainian -> nameUkr ?: nameRus
        Language.English -> nameEng ?: nameRus
        else -> nameRus
    }
}