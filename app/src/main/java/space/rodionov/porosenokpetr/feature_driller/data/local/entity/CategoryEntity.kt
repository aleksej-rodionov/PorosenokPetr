package space.rodionov.porosenokpetr.feature_driller.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    val name: String,
    val isCategoryActive: Boolean = true,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
