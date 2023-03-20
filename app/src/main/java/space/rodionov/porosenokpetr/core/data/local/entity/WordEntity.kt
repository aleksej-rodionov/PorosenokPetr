package space.rodionov.porosenokpetr.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import space.rodionov.porosenokpetr.core.domain.model.Word

@Entity
data class WordEntity(
    val rus: String,
    val ukr: String?,
    val eng: String,
    val swe: String?,
    val categoryName: String,
    val isWordActive: Boolean = true,
    val isWordLearned: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int? = 0
)