package space.rodionov.porosenokpetr.feature_wordeditor.presentation.model

import space.rodionov.porosenokpetr.core.util.Constants
import space.rodionov.porosenokpetr.core.util.Language

data class Translation(
    val language: Language,
    val translation: String
)

data class WordUi(
    val categoryName: String,
    val rus: String,
    val eng: String,
    val ukr: String?,
    val swe: String?,
    val examples: List<String> = emptyList(),
    val wordStatus: Int = Constants.WORD_ACTIVE,
    val id: Int = 0
)