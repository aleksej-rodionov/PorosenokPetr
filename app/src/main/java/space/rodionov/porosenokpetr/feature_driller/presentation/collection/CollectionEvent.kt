package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import space.rodionov.porosenokpetr.feature_driller.domain.models.Category

sealed class CollectionEvent {
    data class onCategoryOnChange(val cat: Category, val isOn: Boolean) : CollectionEvent()
    data class onCategoryClick(val cat: Category): CollectionEvent()
    object onSearchClick: CollectionEvent()
}
