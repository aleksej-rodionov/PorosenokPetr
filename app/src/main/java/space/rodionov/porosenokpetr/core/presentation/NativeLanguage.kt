package space.rodionov.porosenokpetr.core.presentation

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import space.rodionov.porosenokpetr.core.util.Language

class NativeLanguage {
    var current by mutableStateOf<Language>(Language.Russian)
}

val LocalNativeLanguage = compositionLocalOf<NativeLanguage> { error("Unknown language") }