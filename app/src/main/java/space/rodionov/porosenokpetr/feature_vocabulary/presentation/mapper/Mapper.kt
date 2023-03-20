package space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper

import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem

fun Category.toCategoryUi(): VocabularyItem.CategoryUi {
    return VocabularyItem.CategoryUi(name, isCategoryActive,learnedFromActivePercentage, id, nameRus, nameUkr, nameEng)
}

fun VocabularyItem.CategoryUi.toCategory(): Category {
    return Category(name, isCategoryActive, learnedFromActivePercentage, id, nameRus, nameUkr, nameEng)
}

fun Word.toWordUi(): VocabularyItem.WordUi {
    return VocabularyItem.WordUi(rus, ukr, eng, swe, categoryName, isWordActive, isWordLearned, id)
}

fun VocabularyItem.WordUi.toWord(): Word {
    return Word(rus, ukr, eng, swe, categoryName, isWordActive, isWordLearned, id)
}