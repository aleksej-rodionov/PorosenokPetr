package space.rodionov.porosenokpetr.feature_driller.presentation.util

sealed class UiEvent {
    object PopBackStack: UiEvent()
    data class Navigate(val screen: String): UiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent()
}