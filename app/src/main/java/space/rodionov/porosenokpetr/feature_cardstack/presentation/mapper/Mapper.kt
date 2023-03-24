package space.rodionov.porosenokpetr.feature_cardstack.presentation.mapper

import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem

fun Word.toWordUi(): CardStackItem.WordUi {
    return CardStackItem.WordUi(rus, ukr, eng, swe, categoryName, wordStatus, id)
}

fun CardStackItem.WordUi.toWord(): Word {
    return Word(rus, ukr, eng, swe, categoryName, wordStatus, id)
}