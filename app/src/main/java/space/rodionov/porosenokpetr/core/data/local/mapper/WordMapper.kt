package space.rodionov.porosenokpetr.core.data.local.mapper

import space.rodionov.porosenokpetr.core.data.local.entity.WordEntity
import space.rodionov.porosenokpetr.core.data.local.entity.WordRaw
import space.rodionov.porosenokpetr.core.domain.model.Word

fun WordEntity.toWord(): Word {
    return Word(
        eng = eng,
        rus = rus,
        ukr = ukr,
        swe = swe,
        categoryName = categoryName,
        isWordActive = isWordActive,
        isWordLearned = isWordLearned,
        id = id
    )
}

fun Word.toWordEntity(): WordEntity {
    return WordEntity(
        eng = eng,
        rus = rus,
        ukr = ukr,
        swe = swe,
        categoryName = categoryName,
        isWordActive = isWordActive,
        isWordLearned = isWordLearned,
        id = id
    )
}

fun WordRaw.toWord(): Word {
    return Word(
        rus = rus,
        eng = eng,
        swe = swe,
        ukr = ukr,
        categoryName = catName,
        isWordActive = true,
        isWordLearned = false
    )
}