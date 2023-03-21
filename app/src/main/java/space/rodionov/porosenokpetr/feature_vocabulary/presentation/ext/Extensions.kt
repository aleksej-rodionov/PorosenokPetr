package space.rodionov.porosenokpetr.feature_vocabulary.presentation.ext

import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper.toCategoryUi
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper.toWordUi
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

fun Pair<List<Category>, List<Word>>.transformData(): List<VocabularyItem.CategoryUi> {

    val categories = this.first.map { category ->

        val wordsContained = this.second.filter { word ->
            word.categoryName == category.name
        }.map { it.toWordUi() }

        category.toCategoryUi().copy(
            isDisplayedInCollection = wordsContained.isNotEmpty(),
            words = wordsContained
        )
    }

    return categories
}