package space.rodionov.porosenokpetr.core.domain.model

import space.rodionov.porosenokpetr.core.util.Constants.WORD_ACTIVE

data class Word(
    val categoryName: String,
    val rus: String,
    val eng: String,
    val ukr: String? = null,
    val swe: String? = null,
    val examples: List<String> = emptyList(),
    val wordStatus: Int = WORD_ACTIVE,
    val id: Int = 0,
)