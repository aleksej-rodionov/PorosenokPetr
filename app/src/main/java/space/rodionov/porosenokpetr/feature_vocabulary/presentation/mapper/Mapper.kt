package space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper

import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem

fun Category.toCategoryUi(): VocabularyItem.CategoryUi {
    return VocabularyItem.CategoryUi(
        name,
        isCategoryActive,
        learnedFromActivePercentage,
        id,
        nameRus,
        nameUkr ?: nameRus,
        nameEng
    )
}

fun Word.toWordUi(): VocabularyItem.WordUi {
    return VocabularyItem.WordUi(
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

fun VocabularyItem.WordUi.toWord(): Word {
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