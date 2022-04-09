package space.rodionov.porosenokpetr.feature_driller.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category

@Entity
data class CategoryEntity(
    val resourceName: String,
    val isCategoryActive: Boolean = true,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val nameRus: String,
    val nameUkr: String,
    val nameEng: String? = null
) {
    fun toCategory(): Category {
        return Category(
            resourceName = resourceName,
            isCategoryActive = isCategoryActive,
            nameRus = nameRus,
            nameUkr = nameUkr,
            nameEng = nameEng
        )
    }
}
