package space.rodionov.porosenokpetr.core.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

// этот класс не нужен для фильтер-боттомшита, только для списка слов
data class CategoryWithWords(
    @Embedded val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "categoryName"
    )
    val words: List<WordEntity>
)