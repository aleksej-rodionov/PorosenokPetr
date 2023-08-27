package space.rodionov.porosenokpetr.feature_wordeditor.presentation.mapper

import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.model.WordUi

fun Word.toWordUi(): WordUi {
    return WordUi(
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

fun WordUi.toWord(): Word {
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