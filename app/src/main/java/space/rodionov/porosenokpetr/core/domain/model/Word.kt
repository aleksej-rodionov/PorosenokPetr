package space.rodionov.porosenokpetr.core.domain.model

import space.rodionov.porosenokpetr.core.util.Constants.WORD_ACTIVE

data class Word(
    val rus: String,
    val ukr: String? = null,
    val eng: String,
    val swe: String? = null,
    val categoryName: String,
    val wordStatus: Int = WORD_ACTIVE,
    val id: Int = 0,
    val remoteId: String? = null
)