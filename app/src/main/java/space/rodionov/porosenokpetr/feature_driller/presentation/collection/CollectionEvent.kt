package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import space.rodionov.porosenokpetr.feature_driller.domain.models.Category

sealed class CollectionEvent {
//    data class NavigateToWordlistScreen(val cat: Category?) : CollectionEvent(), UiEvent
    data class RefreshCatSwitch(val cat: Category) : CollectionEvent()
//    data class ShowSnackbar(val msg: String) : CollectionEvent(), UiEvent
    data class OnSearchQueryChange(val query: String): CollectionEvent()
}
