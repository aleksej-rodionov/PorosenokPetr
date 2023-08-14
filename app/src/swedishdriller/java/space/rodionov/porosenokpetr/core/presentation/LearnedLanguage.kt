package space.rodionov.porosenokpetr.core.presentation

import androidx.compose.runtime.compositionLocalOf
import space.rodionov.porosenokpetr.core.util.Language

data class LearnedLanguage(
    val learnedLanguage: Language = Language.Swedish
)

val LocalLearnedLanguage = compositionLocalOf<LearnedLanguage> { error("Unknown language") }