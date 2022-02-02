package space.rodionov.porosenokpetr.feature_driller.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val name: String,
    val isCategoryActive: Boolean = true
): BaseModel, Parcelable