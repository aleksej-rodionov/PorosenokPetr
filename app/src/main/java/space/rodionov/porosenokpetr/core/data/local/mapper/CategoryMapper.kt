package space.rodionov.porosenokpetr.core.data.local.mapper

import space.rodionov.porosenokpetr.core.data.local.entity.CategoryEntity
import space.rodionov.porosenokpetr.core.domain.model.Category

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        name,
        isCategoryActive,
        learnedFromActivePercentage = learnedFromActivePercentage,
        id = id,
        nameRus = nameRus,
        nameUkr = nameUkr ?: nameRus,
        nameEng = nameEng
    )
}

fun CategoryEntity.toCategory(): Category {
    return Category(
        name = name,
        isCategoryActive = isCategoryActive,
        learnedFromActivePercentage = learnedFromActivePercentage,
        id = id,
        nameRus = nameRus,
        nameUkr = nameUkr,
        nameEng = nameEng
    )
}