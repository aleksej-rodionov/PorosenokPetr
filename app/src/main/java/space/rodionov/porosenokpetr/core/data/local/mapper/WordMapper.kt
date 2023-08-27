package space.rodionov.porosenokpetr.core.data.local.mapper

import space.rodionov.porosenokpetr.core.data.local.entity.WordEntity
import space.rodionov.porosenokpetr.core.domain.model.Word

fun WordEntity.toWord(): Word {
    return Word(
        categoryName = categoryName,
        rus = rus,
        eng = eng,
        ukr = ukr,
        swe = swe,
        examples = examples,
        wordStatus = wordStatus,
        id = id
    )
}

fun Word.toWordEntity(): WordEntity {
    return WordEntity(
        categoryName = categoryName,
        rus = rus,
        eng = eng,
        ukr = ukr,
        swe = swe,
        examples = examples,
        wordStatus = wordStatus,
        id = id
    )
}