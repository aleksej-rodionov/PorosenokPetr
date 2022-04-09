package space.rodionov.porosenokpetr.feature_driller.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val resourceName: String,
    val isCategoryActive: Boolean = true,
    val nameRus: String,
    val nameUkr: String,
    val nameEng: String? = null
): BaseModel, Parcelable