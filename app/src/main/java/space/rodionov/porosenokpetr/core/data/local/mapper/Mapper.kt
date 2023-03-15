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
        isWordActive = isWordActive
    )
}

fun WordRaw.toWordEntity(): WordEntity {
    return WordEntity(
        rus = rus,
        eng = eng,
        swe = swe,
        ukr = ukr,
        categoryName = catName
    )
}