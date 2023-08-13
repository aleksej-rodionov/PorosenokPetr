package space.rodionov.porosenokpetr.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.util.Constants.WORD_ACTIVE

@Entity
data class WordEntity(
    val rus: String,
    val ukr: String?,
    val eng: String,
    val swe: String?,
    val categoryName: String,
    val wordStatus: Int = WORD_ACTIVE,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)