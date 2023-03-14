package space.rodionov.porosenokpetr.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word

@Entity
data class WordEntity(
    val rus: String,
    val ukr: String?,
    val eng: String,
    val swe: String?,
    val categoryName: String,
    val isWordActive: Boolean = true,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) {
    fun toWord(): Word {
        return Word(
            eng = eng,
            rus = rus,
            ukr = ukr,
            swe = swe,
            categoryName = categoryName,
            isWordActive = isWordActive
        )
    }
}