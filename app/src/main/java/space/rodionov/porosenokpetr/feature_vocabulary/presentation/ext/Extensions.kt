package space.rodionov.porosenokpetr.feature_vocabulary.presentation.ext

import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.CategoryUi

fun List<CategoryUi>.mapCategoriesOnOpenedChanged(
    category: CategoryUi,
    opened: Boolean
): List<CategoryUi> {
    return this.map {
        if (it.id == category.id) it.copy(isOpenedInCollection = opened)
        else it
    }
}