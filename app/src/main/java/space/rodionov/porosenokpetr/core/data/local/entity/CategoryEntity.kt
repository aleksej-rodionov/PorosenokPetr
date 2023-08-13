package space.rodionov.porosenokpetr.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    val name: String,
    val isCategoryActive: Boolean = true,
    val learnedFromActivePercentage: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nameRus: String,
    val nameUkr: String,
    val nameEng: String? = null
)
