package space.rodionov.porosenokpetr.core.data.local.mapper

import space.rodionov.porosenokpetr.core.data.local.entity.CategoryEntity
import space.rodionov.porosenokpetr.core.domain.model.Category

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        resourceName,
        isCategoryActive,
        id = id,
        nameRus = nameRus,
        nameUkr = nameUkr,
        nameEng = nameEng
    )
}

fun CategoryEntity.toCategory(): Category {
    return Category(
        resourceName = resourceName,
        isCategoryActive = isCategoryActive,
        id = id,
        nameRus = nameRus,
        nameUkr = nameUkr,
        nameEng = nameEng
    )
}