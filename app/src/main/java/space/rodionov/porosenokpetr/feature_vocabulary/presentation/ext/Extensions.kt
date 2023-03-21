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

fun List<VocabularyItem>.updateCategoriesInFrontListByActivity(
    allCategories: List<VocabularyItem.CategoryUi>
): List<VocabularyItem> {
    return this.map { item ->
        when (item) {
            is VocabularyItem.CategoryUi -> {
                item.copy(isCategoryActive = allCategories.find { category ->
                    category.name == item.name
                }?.isCategoryActive == true)
            }
            is VocabularyItem.WordUi -> item
        }
    }
}

//fun List<VocabularyItem.WordUi>.compileNewFrontList(
//    allCategories: List<VocabularyItem.CategoryUi>
//): List<VocabularyItem {
//
//}