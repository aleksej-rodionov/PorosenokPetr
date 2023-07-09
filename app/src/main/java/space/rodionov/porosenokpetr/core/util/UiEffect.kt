package space.rodionov.porosenokpetr.core.util

import space.rodionov.porosenokpetr.core.presentation.UiText

sealed class UiEffect {
    data class NavigateTo(val route: String) : UiEffect()
    object NavigateUp: UiEffect()
    data class ShowSnackbar(val msg: UiText): UiEffect()
}