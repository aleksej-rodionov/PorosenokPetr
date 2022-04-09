package space.rodionov.porosenokpetr.feature_driller.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

// этот класс не нужен для фильтер-боттомшита, только для списка слов
data class CategoryWithWords(
    @Embedded val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "nameRus",
        entityColumn = "categoryName"
    )
    val words: List<WordEntity>
)