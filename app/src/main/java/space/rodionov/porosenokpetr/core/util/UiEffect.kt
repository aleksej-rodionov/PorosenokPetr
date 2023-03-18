package space.rodionov.porosenokpetr.core.util

import space.rodionov.porosenokpetr.core.presentation.UiText

sealed class UiEffect {
    object NavigateSuccess: UiEffect()
    object NavigateUp: UiEffect()
    data class ShowSnackbar(val msg: UiText): UiEffect()
}
