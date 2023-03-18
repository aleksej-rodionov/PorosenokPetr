package space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper

import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.CategoryUi

fun Category.toCategoryUi(): CategoryUi {
    return CategoryUi(
        resourceName, isCategoryActive, id, nameRus, nameUkr, nameEng
    )
}

fun CategoryUi.toCategory(): Category {
    return Category(
        resourceName, isCategoryActive, id, nameRus, nameUkr, nameEng
    )
}