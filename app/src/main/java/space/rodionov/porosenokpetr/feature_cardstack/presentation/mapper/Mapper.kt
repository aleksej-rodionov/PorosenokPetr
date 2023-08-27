package space.rodionov.porosenokpetr.feature_cardstack.presentation.mapper

import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem

fun Word.toWordUi(): CardStackItem.WordUi {
    return CardStackItem.WordUi(
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

fun CardStackItem.WordUi.toWord(): Word {
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