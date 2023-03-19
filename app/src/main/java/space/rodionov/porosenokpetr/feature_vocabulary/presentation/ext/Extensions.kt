package space.rodionov.porosenokpetr.feature_vocabulary.presentation.ext

import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem


fun List<VocabularyItem.CategoryUi>.mapCategoriesOnDisplayedChanged(
    category: VocabularyItem.CategoryUi,
    opened: Boolean
): List<VocabularyItem.CategoryUi> {
    return this.map {
        if (it.id == category.id) it.copy(isDisplayedInCollection = opened)
        else it
    }
}