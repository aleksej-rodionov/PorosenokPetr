package space.rodionov.porosenokpetr.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import space.rodionov.porosenokpetr.core.util.Constants.WORD_ACTIVE

@Entity
data class WordEntity(
    val categoryName: String,
    val rus: String,
    val eng: String,
    val ukr: String?,
    val swe: String?,
    val examples: List<String> = emptyList(),
    val wordStatus: Int = WORD_ACTIVE,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)