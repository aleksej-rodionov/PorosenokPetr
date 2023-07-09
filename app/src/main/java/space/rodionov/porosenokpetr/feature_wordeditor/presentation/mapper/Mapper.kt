package space.rodionov.porosenokpetr.feature_wordeditor.presentation.mapper

import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.model.WordUi

fun Word.toWordUi(): WordUi {
    return WordUi(rus, ukr, eng, swe, categoryName, wordStatus, id)
}

fun WordUi.toWord(): Word {
    return Word(rus, ukr, eng, swe, categoryName, wordStatus, id)
}